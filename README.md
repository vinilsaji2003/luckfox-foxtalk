# FoxTalk

A push-to-talk walkie-talkie built on two LuckFox Pico Mini devices (RockChip RV1103G1), running a custom Yocto Linux image.

## Hardware
- Board: LuckFox Pico Mini
- SoC: RockChip RV1103G1 (ARM Cortex-A7 + NPU)
- Audio: I2S microphone + speaker

## Stack
- Bootloader: U-Boot
- Kernel: Linux (mainline / Rockchip BSP)
- Distro: foxtalk-distro (custom Yocto)
- Audio: ALSA (capture + playback)
- Transport: RTP/RTCP over WiFi
- Discovery: mDNS (Avahi)
- PTT: GPIO push button

## Milestones
- [ ] v0.1.0 — Board boots custom Yocto image
- [ ] v0.2.0 — ALSA mic capture and speaker playback working
- [ ] v0.3.0 — RTP audio stream between two devices
- [ ] v0.4.0 — GPIO push-to-talk button
- [ ] v0.5.0 — mDNS peer discovery
- [ ] v1.0.0 — Release image, minimal footprint, read-only rootfs
