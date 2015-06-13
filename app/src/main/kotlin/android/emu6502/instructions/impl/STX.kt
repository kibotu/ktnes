package android.emu6502.instructions.impl

import android.emu6502.CPU
import android.emu6502.instructions.BaseInstruction
import android.emu6502.instructions.Instruction

/** STore X register */
class STX(cpu: CPU)
: BaseInstruction(Instruction.STX, cpu.instructionList) {
}