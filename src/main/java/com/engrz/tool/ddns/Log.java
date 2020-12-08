package com.engrz.tool.ddns;

import lombok.experimental.UtilityClass;

import java.util.logging.Logger;

/**
 * @author Engr-Z
 * @since 2020/12/8
 */
@UtilityClass
public class Log {

    private final Logger log = Logger.getLogger("ddns");

    /**
     *
     * @param msg
     */
    public void debug(String msg) {

        log.info(msg);
    }

    /**
     *
     * @param msg
     */
    public void info(String msg) {

        log.info(msg);
    }

}
