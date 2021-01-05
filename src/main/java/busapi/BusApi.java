package busapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vo.StationInfoVO;

public class BusApi {
	private String servicekey = "serviceKey=2PfpB3MF8QvD%2FE%2FL%2BzfLO9LtIDHsnCi1Ae6D9Vx7SspQ4zvD3Upsnj3zZBLYm8SrVUugQrdOm3ogJUfaVtbcwQ%3D%3D&";
	//공고데이터에서 발급 받은 api 키 입니다. 변경 ㄴㄴ
	
	private static String getTagValue(String tag , Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		
		Node nValue = (Node) nlList.item(0);
		if(nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}
	
	public List<StationInfoVO> busInfo(String station_id) { 
		//유효성 검사를 한 station_id을 파라미터로 받아서 vo저장 후 list로 반환 
		// dao와 같은 부류라고 생각하면됨										
		List<StationInfoVO> list = new ArrayList<StationInfoVO>();

		try {
			String bus_url = "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?" + servicekey + station_id; //해당 정류소에 버스가 언제오는지 조회
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance(); //xml을 파싱하기 위한 라이브러리 인스턴스화
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		
			Document doc = dBuilder.parse(bus_url);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		
			 NodeList nList = doc.getElementsByTagName("itemList");
			 System.out.println("버스 검색 수량:" + nList.getLength() );
			 
			for( int temp=0; temp < nList.getLength(); temp++ ){
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					StationInfoVO vo = new StationInfoVO();
					vo.setStation(getTagValue("stNm",eElement));// 조회될 정류소명
					vo.setBus_name(getTagValue("rtNm", eElement)); // 버스명
					vo.setNowBus(getTagValue("arrmsg1", eElement));// 도착시간
					vo.setNextBus(getTagValue("arrmsg2", eElement)); // 다음버스 도착시간
					list.add(vo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	


	public String busNameCheck(String b_id) { // 버스 정류소명 검사 및 id로 변환
		
		//String station = "stId=100000023";
		String station_id = ""; //버스정류장 id 가 담겨 있는 변수
		
		System.out.println("정류소: " +  b_id); //jsp 에서 받아온거를 표시
		
		//------------------------------------ 
		//버스 정류소 조회 서버에서 값 불러오기 (예: 여의도 환승센터라고 검색하면 자동으로 118000513 변환) 
		
		try {
			b_id = URLEncoder.encode(b_id, "UTF-8"); //jsp에서 받은 값을 API 서버로 보내야 하는데 이때 한글이 깨져서 인코딩함.
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String station_url = "http://openapi.gbis.go.kr/ws/rest/busstationservice?" + servicekey + "keyword=" +  b_id; //버스 ID 조회하는 URL 
		StringBuffer sbuf = new StringBuffer();

		
		try { //버스 정류소를 조회 합니다.
			
			//System.out.println( "요청: " + station_url );
			URL url = new URL(station_url); //버스 정류소 ID 조회를 요청함
			URLConnection urlConn = url.openConnection();
            InputStream is = urlConn.getInputStream(); //담아서
            InputStreamReader isr = new InputStreamReader(is, "UTF-8"); //한글 깨지는거 있어서 인코딩함
            BufferedReader br = new BufferedReader(isr); // 버퍼에 담음
             
            String str ;
            while((str=br.readLine()) != null){ //저장함
                sbuf.append(str + "\r\n") ;
            }
            //System.out.println(sbuf.toString()) ;
            //----------------------------------------
            
            
            if(sbuf.toString().contains("결과가 존재하지 않습니다.")) { //만약에 검색을 했는데 그런 정류소 가 없으면 이렇게 뜸
            	System.out.println( "해당 결과값은 존재하지 않습니다. 다시 검색해주세요" ) ;
            	return "no"; //정류장이 없을때
            }else { // 조회하고자 하는 정류소가 있으면 station 에 값이 담김 , 2개 이상인경우 첫번째만 값이 저장됨 , 이 station 기반으로 정류소에서 버스 검색함
            	String buf[] = sbuf.toString().split("</");
            	for(int i=0; i<buf.length; i++) {
            		if(buf[i].contains("regionName><stationId>") ) {
            			buf[i] = buf[i].replace("regionName><stationId>",""); //자르기
            			station_id = "stId=" + buf[i];
            			
            			System.out.println("정류소 이름 -> id 변환: "  + station_id) ;
            			break;
            		}
            	}
            	return station_id; // 정상처리 (정류소 이름을 id반환)
            }
		}catch (Exception e) { 
			e.printStackTrace();
		}
		return "error"; // 오류
	}
	
	public String gps_station_check(String lat_1 , String lng_1) { // 좌표로 정류소id 반환 
		String station = ""; //버스정류장 id 가 담겨 있는 변수
		//String station = "stId=100000023";
		
		System.out.println("좌표값: " +  lat_1 + "/" + lng_1 ); //jsp 에서 받아온거를 표시
		
		//------------------------------------ 
		//버스 정류소 조회 서버에서 값 불러오기 (예: 여의도 환승센터라고 검색하면 자동으로 118000513 변환) 
		
		try {
			lat_1 = URLEncoder.encode(lat_1, "UTF-8"); //jsp에서 받은 값을 API 서버로 보내야 하는데 이때 한글이 깨져서 인코딩함.
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			lng_1 = URLEncoder.encode(lng_1, "UTF-8"); //jsp에서 받은 값을 API 서버로 보내야 하는데 이때 한글이 깨져서 인코딩함.
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String station_url = "http://openapi.gbis.go.kr/ws/rest/busstationservice/searcharound?" + servicekey + "&x=" +  lng_1 + "&y=" + lat_1; //버스 ID 조회하는 URL 
		StringBuffer sbuf = new StringBuffer();

		try { //버스 정류소를 조회 합니다.
			
			//System.out.println( "요청: " + station_url );
			URL url = new URL(station_url); //버스 정류소 ID 조회를 요청함
			URLConnection urlConn = url.openConnection();
            InputStream is = urlConn.getInputStream(); //담아서
            InputStreamReader isr = new InputStreamReader(is, "UTF-8"); //한글 깨지는거 있어서 인코딩함
            BufferedReader br = new BufferedReader(isr); // 버퍼에 담음
             
            String str ;
            while((str=br.readLine()) != null){ //저장함
                sbuf.append(str + "\r\n") ;
            }
            //System.out.println(sbuf.toString()) ;
            //----------------------------------------
            
            if(sbuf.toString().contains("결과가 존재하지 않습니다.")) { //만약에 검색을 했는데 그런 정류소 가 없으면 이렇게 뜸
            	System.out.println( "해당 결과값은 존재하지 않습니다. 다시 검색해주세요" ) ;
            
            }else { // 조회하고자 하는 정류소가 있으면 station 에 값이 담김 , 2개 이상인경우 첫번째만 값이 저장됨 , 이 station 기반으로 정류소에서 버스 검색함
            	String buf[] = sbuf.toString().split("</");
            	for(int i=0; i<buf.length; i++) {
            		if(buf[i].contains("regionName><stationId>") ) {
            			buf[i] = buf[i].replace("regionName><stationId>",""); //자르기
            			station = "stId=" + buf[i];
            			
            			System.out.println("정류소 이름 -> id 변환: "  + station) ;
            			break;
            		}
            		
            	}
            }
            
    	}catch(Exception e) {
    		System.out.print("error: " + e);
    		station = "error";
    	}		
		
		return station;
	}
	
	
}
