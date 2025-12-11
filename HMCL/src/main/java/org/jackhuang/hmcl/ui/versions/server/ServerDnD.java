/*
 * Hello Minecraft! Launcher
 * Copyright (C) 2025  huangyuhui <huanghongxun2008@126.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.jackhuang.hmcl.ui.versions.server;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.Optional;
import java.util.function.Consumer;

import static org.jackhuang.hmcl.util.io.NetworkUtils.decodeURL;

public class ServerDnD {
    private static final String SCHEME = "minecraft-server";

    private ServerDnD() {}

    // format
    // minecraft-server:<server-name>:<server-ip>:[server-icon]
    public static Optional<ServerData> parseUrlFromDragboard(Dragboard dragboard) {
        String data = dragboard.getString();
        if (data == null) return Optional.empty();

        String[] elements = data.split(":");
        if(elements.length < 3) return Optional.empty();
        if (!SCHEME.equals(elements[0])) return Optional.empty();

        String name = decodeURL(elements[1]);
        String ip = decodeURL(elements[2]);
        String icon = elements.length >= 4 ? decodeURL(elements[3]) : null;
        return Optional.of(ServerData.createServerData(name, ip, icon));
    }


    public static EventHandler<DragEvent> dragOverHandler() {
        return event -> parseUrlFromDragboard(event.getDragboard()).ifPresent(url -> {
            event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });
    }

    public static EventHandler<DragEvent> dragDroppedHandler(Consumer<ServerData> onServerDataTransfered) {
        return event -> parseUrlFromDragboard(event.getDragboard()).ifPresent(serverData -> {
            event.setDropCompleted(true);
            event.consume();
            onServerDataTransfered.accept(serverData);
        });
    }
}
