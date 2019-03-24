package com.thoughtworks.util;

import com.thoughtworks.exception.BattleShipException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.constant.BattleShipConstant.*;
import static com.thoughtworks.constant.BattleShipConstant.Error.BATTLEFIELD_HEIGHT_VIOLATION;
import static com.thoughtworks.constant.BattleShipConstant.Error.BATTLEFIELD_WIDTH_VIOLATION;

public class InputFileUtil
{
    private static List<String> fileData = new ArrayList<String>();
    static
    {
        InputStream inputStream = InputFileUtil.class.getResourceAsStream("/input.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        try{
            while((line = bufferedReader.readLine()) != null)
            {
                 if(!line.equals(""))
                     fileData.add(line);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static Integer getWidthOfBattleField(){
        String inputData[] = fileData.get(0).split(DELIMITER);
        int width = Integer.parseInt(inputData[0]);
        if(width < MIN_WIDTH && width > MAX_WIDTH)
            throw new BattleShipException(BATTLEFIELD_WIDTH_VIOLATION.getMessage());
        else
            return width;
    }
    public static Character getHeightOfBattleField(){
        String inputData[] = fileData.get(0).split(DELIMITER);
        Character c = inputData[1].charAt(0);
        if(c < MIN_HEIGHT && c > MAX_HEIGHT)
            throw new BattleShipException(BATTLEFIELD_HEIGHT_VIOLATION.getMessage());
        else
            return c;
    }
    public static Integer getSizeOfBattleField()throws IllegalAccessException{
        Integer width = getWidthOfBattleField();
        Integer height = (Integer)(getHeightOfBattleField()-64);
        return width * height;
    }
    public static Integer getNoOfBattleShipsForEachPlayer()
    {
        return Integer.parseInt(fileData.get(1));
    }

    public static List<String> getShipsData()
    {
        int counter = 0;
        List<String> shipData = new ArrayList<>();
        int baseRow = 2;
        int getTotalShips = getNoOfBattleShipsForEachPlayer();
        while(counter < getTotalShips)
        {
            shipData.add(fileData.get(baseRow+counter++));
        }
        return shipData;
    }

    public static List<String> getTargetsForPlayer(int playerNumber)
    {
        List<String> targets = new ArrayList<>();
        for (String string:fileData.get(fileData.size()-3+ playerNumber).split(DELIMITER))
        {
            targets.add(string);
        }
        return targets;
    }


    public static Map<String,String> getDataOfShipUtil(String dataString)
    {
        String str[] = dataString.split(DELIMITER);
        Map<String,String> map = new HashMap<>();
        map.put(TYPE,str[0]);
        map.put(WIDTH,str[1]);
        map.put(HEIGHT,str[2]);
        map.put(P1_START_INDEX,str[3]);
        map.put(P2_START_INDEX,str[4]);
        return map;
    }
    public static List<Map<String,String>> getListOfShipDataMap()
    {
        List<Map<String,String>> listOfShipDataMap = new ArrayList<>();

        for (String string:getShipsData())
        {
            listOfShipDataMap.add(getDataOfShipUtil(string));
        }
        return listOfShipDataMap;
    }


}
