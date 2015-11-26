package test;

import org.apache.log4j.Logger;

public class Log4J {

    public static final Logger log = Logger.getLogger(Log4J.class);

    public static void main(String[] args){

        //TRACE<DEBUG<INFO<WARN<ERROR<FATAL
        log.info("Test Message");
        log.error("Error");
        log.debug("Debug");
        log.trace("Trace");
        log.warn("Warn");
        log.fatal("Fatal");
    }

}
