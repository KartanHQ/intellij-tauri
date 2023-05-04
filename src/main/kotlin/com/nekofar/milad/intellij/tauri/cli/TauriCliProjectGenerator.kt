package com.nekofar.milad.intellij.tauri.cli

import com.intellij.execution.filters.Filter
import com.intellij.lang.javascript.boilerplate.NpmPackageProjectGenerator
import com.intellij.lang.javascript.boilerplate.NpxPackageDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.vfs.VirtualFile
import com.nekofar.milad.intellij.tauri.TauriBundle
import com.nekofar.milad.intellij.tauri.TauriIcons
import javax.swing.Icon

class TauriCliProjectGenerator : NpmPackageProjectGenerator() {
    private val packageName = "create-tauri-app"
    private val npxCommand = "create-tauri-app"

    override fun getName(): String = TauriBundle.message("tauri.project.generator.name")

    override fun getDescription(): String = TauriBundle.message("tauri.project.generator.description")

    override fun filters(project: Project, baseDir: VirtualFile): Array<Filter> = emptyArray()

    override fun customizeModule(virtualFile: VirtualFile, contentEntry: ContentEntry?) {}

    override fun packageName(): String = packageName

    override fun presentablePackageName(): String =
        TauriBundle.message("tauri.project.generator.presentable.package.name")

    override fun getNpxCommands() = listOf(NpxPackageDescriptor.NpxCommand(packageName, npxCommand))

    override fun generateInTemp(): Boolean = false

    override fun generatorArgs(project: Project?, dir: VirtualFile?, settings: Settings?): Array<String> =
        arrayOf(".")

    override fun getIcon(): Icon = TauriIcons.ProjectGenerator
}
