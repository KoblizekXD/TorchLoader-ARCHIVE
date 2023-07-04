package io.github.koblizekxd.torch.minecraft

class MinecraftProject {
    /**
     * Specifies the mapping type, different version can have different mappings,
     * we currently support these mapping types:
     * - 1.18.2 -  official
     */
    lateinit var mappings: String

    /**
     * Game version, for example: 1.18.2
     * All supported version can be found in [mappings]
     */
    lateinit var version: String
}