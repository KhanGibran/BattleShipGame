package com.thoughtworks.constant;

public class BattleShipConstant
{
    public static final String HIT = "hit";
    public static final String MISS = "miss";

    public static final String WON = " won the battle.";
    public static final String DRAW = "Game ends in a Draw.";

    public static final String TYPE = "type";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String P1_START_INDEX = "p1StartIndex";
    public static final String P2_START_INDEX = "p2StartIndex";

    public static final Integer MIN_WIDTH = 1;
    public static final Integer MAX_WIDTH = 9;

    public static final Character MIN_HEIGHT = 'A';
    public static final Character MAX_HEIGHT = 'Z';

    public static final String DELIMITER = " ";

    public enum Error {

        SHIPS_OVERLAPPING("101", "Ships Arranging/Overlapping Error. Please re-arrange ships first."),
        BATTLEFIELD_WIDTH_VIOLATION("102", "ERROR! WIDTH OF BATTLE FIELD CANNOT BE < 1 and > 9"),
        BATTLEFIELD_HEIGHT_VIOLATION("103", "ERROR! HEIGHT OF BATTLE FIELD CANNOT BE  < A and > Z");

        String code;
        String message;

        private Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public enum ShipType{

        P("P",1),
        Q("Q",2);

        private String type;
        private Integer strength;

        private ShipType(String type,Integer strength)
        {
            this.type=type;
            this.strength=strength;
        }

        public String getType() {
            return type;
        }

        public Integer getStrength() {
            return strength;
        }

        public static Integer getStrengthByType(String type)
        {
            for (ShipType shipType:ShipType.values())
            {
                if(shipType.getType().equalsIgnoreCase(type))
                {
                    return shipType.getStrength();
                }
            }
            return null;
        }
        @Override
        public String toString(){
            return this.type;
        }
    }
}
