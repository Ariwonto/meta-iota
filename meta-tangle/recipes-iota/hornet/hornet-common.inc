SUMMARY = "HORNET is a lightweight alternative to IOTA's fullnode software IRI"
DESCRIPTION = "HORNET is a lightweight alternative to IOTA's fullnode software IRI. The main advantage is that it compiles to native code and does not need a Java Virtual Machine, which considerably decreases the amount of needed resources while significantly increasing the performance. This way, HORNET is easier to install and runs on low-end devices."

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "git://github.com/gohornet/hornet.git;protocol=https;name=hornet;destsuffix=${PN}-${PV}/src/github.com/gohornet/hornet"

SRC_URI += " \
           file://hornet_update_snapshot \
"

DEPENDS = " bash useradd-beekeeper"
RDEPENDS_${PN} = "bash sudo"
RDEPENDS_${PN}-dev = "bash sudo"

inherit go pkgconfig
GO_IMPORT = "github.com/gohornet/hornet"
GO_LINKSHARED = ""
