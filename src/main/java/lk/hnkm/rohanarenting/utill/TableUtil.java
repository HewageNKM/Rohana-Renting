/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/26/23, 11:07 AM
 *
 */

package lk.hnkm.rohanarenting.utill;

import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.*;
import lk.hnkm.rohanarenting.utill.notification.TopUpNotifications;

public class TableUtil {
    static final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
    public static void installCopy(TableView<?> table) {
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setOnKeyPressed(event -> {
            if (keyCodeCopy.match(event)) {
                // Copy selection to clipboard
                copySelectionToClipboard(table);
            }
        });
    }
    private static void copySelectionToClipboard(TableView<?> table) {
        // Get the selected cells
        ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();

        // Initialize a string builder
        StringBuilder clipboardString = new StringBuilder();

        // Initialize a variable to track the previous row
        int prevRow = -1;

        // Loop over the selected cells
        for (TablePosition position : positionList) {
            // Get the row and column indices
            int row = position.getRow();
            int col = position.getColumn();

            // Get the cell data
            Object cell = table.getColumns().get(col).getCellData(row);

            // Replace null values with empty strings
            if (cell == null) {
                cell = "";
            }else {
                TopUpNotifications.copied(cell.toString());
                if (prevRow == row) {
                    clipboardString.append('\t');
                } else if (prevRow != -1) {
                    clipboardString.append('\n');
                }

                // Convert the cell data to a string
                String text = cell.toString();

                // Append the cell data to the string builder
                clipboardString.append(text);

                // Update the previous row
                prevRow = row;
            }
        }

        // Create a clipboard content object
        final ClipboardContent clipboardContent = new ClipboardContent();

        // Set its string content to the string builder's output
        clipboardContent.putString(clipboardString.toString());

        // Set the system clipboard's content to the clipboard content object
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }
}
