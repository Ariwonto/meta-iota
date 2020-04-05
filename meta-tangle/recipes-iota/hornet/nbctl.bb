SUMMARY = "cli utility to manage Hornet neighbors"
DESCRIPTION = "Taken from github.com/nuriel77/hornet-playbook"
AUTHOR = "Nuriel Shem-Tov <nurielst@hotmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://nbctl"

RDEPENDS_${PN} = "python-pyyaml python-ipaddress python-urllib3"

do_install() {

    install -m 0755 -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/nbctl ${D}${bindir}

}
