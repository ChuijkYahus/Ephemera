package net.beholderface.ephemera.casting.patterns.spells.great

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import net.beholderface.ephemera.casting.ChunkLoadingManager
import net.minecraft.entity.Entity
import ram.talia.hexal.api.spell.casting.IMixinCastingContext
import ram.talia.hexal.api.spell.mishaps.MishapNoWisp

class OpLoadChunk : ConstMediaAction {
    override val argc = 0
    override val isGreat = true
    override val mediaCost = MediaConstants.DUST_UNIT / 10
    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val mCast = ctx as? IMixinCastingContext
        if (mCast == null || !mCast.hasWisp())
            throw MishapNoWisp()
        val entry = ChunkLoadingManager.createEntry((mCast.wisp as Entity).chunkPos, ctx.world, 20, 1)
        entry.setForced(true)
        return listOf()
    }
}