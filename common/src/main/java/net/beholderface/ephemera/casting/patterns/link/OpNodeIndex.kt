package net.beholderface.ephemera.casting.patterns.link

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapInvalidIota
import net.beholderface.ephemera.api.getConnected
import net.beholderface.ephemera.blocks.RelayTPDetectorBlock
import net.beholderface.ephemera.blocks.blockentity.RelayIndexBlockEntity
import net.beholderface.ephemera.registry.EphemeraBlockRegistry
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import ram.talia.hexal.api.linkable.LinkableRegistry

class OpNodeIndex() : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.DUST_UNIT
    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val sourceNode = LinkableRegistry.linkableFromIota(args[0], ctx.world)
            ?: throw MishapInvalidIota.ofType(args[0], 0, "linkable")
        val soughtKey = args[1]
        val connectedNodes = sourceNode.getConnected(32)
        for (node in connectedNodes){
            for (dir in Direction.values()){
                val checkedPos = node.getPosition().add(dir.vector.x.toDouble(), dir.vector.y.toDouble(), dir.vector.z.toDouble())
                val state2 = ctx.world.getBlockState(BlockPos(checkedPos))
                val block = state2.block
                if (block == EphemeraBlockRegistry.RELAY_INDEX.get()){
                    val be = ctx.world.getBlockEntity(BlockPos(checkedPos)) as RelayIndexBlockEntity
                    val iota = be.storedIota
                    if (iota.equals(soughtKey)){
                        return node.asActionResult
                    }
                }
            }
        }
    }
}