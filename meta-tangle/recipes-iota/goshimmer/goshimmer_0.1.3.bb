require ${PN}_${PV}.inc

inherit systemd

SRC_URI += " \
             file://goshimmer.service \
"

FILES_${PN} += " goshimmer.service"

CONFFILES_${PN} = "${sysconfdir}/goshimmer/config.json"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "goshimmer.service"
SYSTEMD_AUTO_ENABLE = "disable"

# avoid tests
PTEST_ENABLED="0"

# avoid cache disabling
export GOCACHE = "${B}/.cache"

do_compile_prepend(){

    # avoid tests
    rm -rf ${S}/src/github.com/iotaledger/goshimmer/packages/binary/tangle/model/transaction/test
}

do_install_append(){

    # create goshimmer directory in /etc
    install -m 0755 -d ${D}${sysconfdir}/goshimmer

    # populate config.json
    install -m 0644 ${WORKDIR}/${PN}-${PV}/src/github.com/iotaledger/goshimmer/config.json ${D}${sysconfdir}/goshimmer

    # create systemd directory
    install -m 0755 -d ${D}${systemd_system_unitdir}

    # populate systemd service file
    install -m 0755 ${WORKDIR}/goshimmer.service ${D}${systemd_system_unitdir}
}

pkg_postinst_ontarget_${PN}(){
OPTS=""

if [ -n "$D" ]; then
    OPTS="--root=$D"
fi

if type systemctl >/dev/null 2>/dev/null; then
	if [ -z "$D" ]; then
		systemctl daemon-reload
	fi

	# if goshimmer user doesn't exist, create it
	if [ ! $(getent passwd goshimmer) ]; then
		useradd --no-create-home --system goshimmer > /dev/null
	fi

	# if /var/lib/goshimmer doesn't exist, create it
	if [ ! -d /var/lib/goshimmer ]; then
		mkdir -p /var/lib/goshimmer
		chown -R goshimmer:goshimmer /var/lib/goshimmer
	fi

	systemctl $OPTS disable goshimmer.service

	if [ -z "$D" -a "disable" = "enable" ]; then
		systemctl --no-block restart goshimmer.service
	fi
fi
}

pkg_postrm_${PN}(){
OPTS=""

if [ -n "$D" ]; then
    OPTS="--root=$D"
fi

systemctl --system daemon-reload >/dev/null || true

case "$1" in
remove)
    rm -rf /var/lib/goshimmer/*
    rm -rf /etc/goshimmer/*
    ;;
purge)
    rm -rf /var/lib/goshimmer
    ;;
upgrade | failed-upgrade | abort-install | abort-upgrade | disappear) ;;

*)
    echo "postrm called with unknown argument \`$1'" >&2
    exit 1
    ;;
esac
}