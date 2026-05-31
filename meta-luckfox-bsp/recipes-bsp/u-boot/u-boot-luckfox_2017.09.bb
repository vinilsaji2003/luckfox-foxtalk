SUMMARY = "Rockchip vendor U-Boot for LuckFox Pico Mini B (RV1103G1)"
DESCRIPTION = "Rockchip vendor U-Boot 2017.09 fork for RV1103G1/RV1106 SoC family"
HOMEPAGE = "https://github.com/rockchip-linux/u-boot"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "bc-native dtc-native python3-native"

SRCREV = "b14196eade471bbc000c368f8555f2a2a1ecc17d"
SRCREV_rkbin = "74213af1e952c4683d2e35952507133b61394862"

SRCREV_FORMAT = "default_rkbin"

SRC_URI = " git://github.com/rockchip-linux/u-boot.git;protocol=https;branch=next-dev;name=default \
	git://github.com/rockchip-linux/rkbin.git;protocol=https;branch=master;name=rkbin;destsuffix=rkbin \
	file://rv1106_miniall_spi_nand.ini \
	"

S = "${WORKDIR}/git"
B = "${S}"

COMPATIBLE_MACHINE = "luckfox-pico-mini-b"

RKBIN_TEE = "bin/rv11/rv1106_tee_ta_v1.13.bin"

inherit deploy

EXTRA_OEMAKE = " \
    HOSTCC='gcc' \
    HOSTCXX='g++' \
    CROSS_COMPILE=${TARGET_PREFIX} \
    PYTHON=python3 \
    KCFLAGS='-Wno-error' \
"

do_configure(){
	oe_runmake ${UBOOT_MACHINE}
}

do_compile(){
	cp ${WORKDIR}/rkbin/${RKBIN_TEE} ${S}/tee.bin
	oe_runmake
	sed -i "s|@RKBIN@|${WORKDIR}/rkbin|g" ${WORKDIR}/rv1106_miniall_spi_nand.ini
	sed -i "s|@UBOOT_BUILD@|${B}|g" ${WORKDIR}/rv1106_miniall_spi_nand.ini
	${WORKDIR}/rkbin/tools/boot_merger ${WORKDIR}/rv1106_miniall_spi_nand.ini
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/rv1106_idblock_v1.15.100.img ${DEPLOYDIR}/idblock.img
    install -m 0644 ${B}/u-boot.itb ${DEPLOYDIR}/u-boot.itb
}

addtask deploy before do_build after do_compile

PROVIDES += "virtual/bootloader"

INSANE_SKIP:${PN} = "ldflags"  
