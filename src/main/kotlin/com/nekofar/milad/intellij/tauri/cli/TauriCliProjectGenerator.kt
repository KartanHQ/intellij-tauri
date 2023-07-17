package com.nekofar.milad.intellij.tauri.cli

import com.intellij.execution.filters.Filter
import com.intellij.lang.javascript.boilerplate.NpmPackageProjectGenerator
import com.intellij.lang.javascript.boilerplate.NpxPackageDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.vfs.VirtualFile
import com.nekofar.milad.intellij.tauri.TauriBundle.message
import com.nekofar.milad.intellij.tauri.TauriIcons

class TauriCliProjectGenerator : NpmPackageProjectGenerator() {
    private val packageName = "create-tauri-app"
    private val npxCommand = "create-tauri-app"

    override fun getName() = message("tauri.project.generator.name")

    override fun getDescription() = message("tauri.project.generator.description")

    override fun filters(project: Project, baseDir: VirtualFile) = emptyArray<Filter>()

    override fun customizeModule(virtualFile: VirtualFile, contentEntry: ContentEntry?) {}

    override fun packageName(): String = packageName

    override fun presentablePackageName() = message("tauri.project.generator.presentable.package.name")

    override fun getNpxCommands() = listOf(NpxPackageDescriptor.NpxCommand(packageName, npxCommand))

    override fun generateInTemp() = true

    override fun generatorArgs(project: Project, baseDir: VirtualFile) = arrayOf(project.name)

    override fun getIcon() = TauriIcons.ProjectGenerator
}
