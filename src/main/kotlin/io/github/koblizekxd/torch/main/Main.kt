package io.github.koblizekxd.torch.main

import io.github.koblizekxd.torch.minecraft.MinecraftProject

fun main() {
    minecraft {
        mappings = "1.12.2-official"
    }
}

fun minecraft(proj: MinecraftProject.() -> Unit): MinecraftProject {
    val mc = MinecraftProject()
    proj(mc)
    return mc
}