require conf/distro/poky.conf

DISTRO = "honeycomb"
DISTRO_NAME = "IOTA honeycombOS"
DISTRO_VERSION = "0.2.0"

MAINTAINER = "Bernardo A. Rodrigues <bernardoaraujor@gmail.com>"

GOVERSION = "1.13%"

DISTRO_FEATURES += " \
                   systemd \
                   wifi \
"

VIRTUAL-RUNTIME_init_manager = "systemd"

PACKAGE_CLASSES = "package_deb"

INHERIT += "extrausers"
EXTRA_USERS_PARAMS = "usermod -P tangleisawesome root;"
