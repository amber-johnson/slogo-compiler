module slogo_team05 {
    requires java.xml;
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;

    exports slogo.frontend;
    exports slogo.backend.commands;
    exports slogo.backend.exceptions;
    exports slogo.backend.external_api;

    exports slogo.backend.utils;
    exports slogo;
}
