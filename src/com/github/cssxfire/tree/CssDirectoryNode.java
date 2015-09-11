/*
 * Copyright 2012 Ronnie Kolehmainen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.cssxfire.tree;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CssDirectoryNode extends CssTreeNode {
    @NotNull  private final PsiDirectory directory;

    public CssDirectoryNode(@NotNull PsiDirectory directory) {
        this.directory = directory;
    }

    @Override
    public Icon getIcon() {
        ItemPresentation presentation = directory.getPresentation();
        return presentation != null ? presentation.getIcon(true) : directory.getIcon(Iconable.ICON_FLAG_VISIBILITY | Iconable.ICON_FLAG_READ_STATUS);
    }

    @Override
    public String getName() {
        Project project = directory.getProject();
        VirtualFile baseDir = project.getBaseDir();
        String projectPath = baseDir != null ? baseDir.getPath() : null;

        String directoryPath = directory.getVirtualFile().getPath();
        if (projectPath != null && directoryPath.length() > projectPath.length() && directoryPath.startsWith(projectPath)) {
            return directoryPath.substring(projectPath.length() + 1);
        }
        return directoryPath;
    }

    @Override
    public String getText() {
        return getName() + " (" + TreeUtils.countLeafs(this) + ")";
    }

    @Override
    public ActionGroup getActionGroup() {
        return (ActionGroup) ActionManager.getInstance().getAction("IncomingChanges.DeclarationNodePopup.Directory");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CssDirectoryNode that = (CssDirectoryNode) o;

        return directory.equals(that.directory);

    }

    @Override
    public int hashCode() {
        return directory.hashCode();
    }
}
