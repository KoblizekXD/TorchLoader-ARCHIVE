package io.github.koblizekxd.torch.util

import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.UnknownConfigurationException
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.internal.impldep.org.eclipse.jgit.annotations.NonNull
import java.util.*


class DependencyConfiguration(@NonNull project: Project, @NonNull configurationName: String) {
    private var dependencyHandler: DependencyHandler
    private var configurationName: String

    init {
        dependencyHandler = project.dependencies
        this.configurationName = configurationName
        ensureDependencyConfigurationExists(project)
    }

    fun add(@NonNull dependencyNotation: Any): Optional<Dependency> {
        return Optional.ofNullable(dependencyHandler.add(configurationName, dependencyNotation))
    }

    fun add(@NonNull dependencyNotation: Any, @NonNull closure: Closure<*>?): Optional<Dependency> {
        return Optional.ofNullable(dependencyHandler.add(configurationName, dependencyNotation, closure))
    }

    fun ensureDependencyConfigurationExists(@NonNull project: Project) {
        try {
            project.configurations.getByName(configurationName).dependencies
        } catch (e: UnknownConfigurationException) {
            if ("implementation" == configurationName) throw RuntimeException(
                "Are you missing the `java` plugin in your build.gradle?",
                e
            )
            throw e
        }
    }
}