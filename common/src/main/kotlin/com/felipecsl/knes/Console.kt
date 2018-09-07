package com.felipecsl.knes

internal class Console(
    private val cpu: CPU,
    private val apu: APU,
    private val ppu: PPU,
    private val mapper: Mapper
) {
  fun step(): Long {
    val cpuCycles = cpu.step()
    for (it in 0 until cpuCycles * 3) {
      if (!ppu.step()) {
        mapper.step()
      }
    }
    for (it in 0 until cpuCycles) {
      apu.step()
    }
    return cpuCycles
  }

  fun buffer(): Bitmap {
    return ppu.front
  }

  fun reset() {
    cpu.reset()
  }

  companion object {
    fun newConsole(
        cartridge: Cartridge,
        bitmapFactory: (Int, Int) -> Bitmap,
        mapperCallback: MapperStepCallback? = null,
        cpuCallback: CPUStepCallback? = null,
        ppuCallback: PPUStepCallback? = null,
        ppu: PPU = PPU(cartridge, bitmapFactory, ppuCallback),
        controller1: Controller = Controller(),
        controller2: Controller = Controller(),
        apu: APU = APU(),
        mapper: Mapper = Mapper.newMapper(cartridge, mapperCallback),
        cpu: CPU = CPU(mapper, ppu, apu, controller1, controller2, IntArray(2048), cpuCallback)
    ): Console {
      val console = Console(cpu, apu, ppu, mapper)
      ppu.cpu = cpu
      ppu.mapper = mapper
      mapper.cpu = cpu
      return console
    }
  }
}
