package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.LatLon;
import it.polito.tdp.flight.model.Route;

public class FlightDAO {

	public List<Airline> getAllAirlines() {
		String sql = "SELECT * FROM airline";
		List<Airline> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Airline(res.getInt("Airline_ID"), res.getString("Name"), res.getString("Alias"),
						res.getString("IATA"), res.getString("ICAO"), res.getString("Callsign"),
						res.getString("Country"), res.getString("Active")));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public List<Route> getAllRoutes() {
		String sql = "SELECT * FROM route";
		List<Route> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Route(res.getString("Airline"), res.getInt("Airline_ID"), res.getString("Source_airport"),
						res.getInt("Source_airport_ID"), res.getString("Destination_airport"),
						res.getInt("Destination_airport_ID"), res.getString("Codeshare"), res.getInt("Stops"),
						res.getString("Equipment")));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public List<Airport> getAllAirports(Map<Integer, Airport> idMap) {
		String sql = "SELECT * FROM airport";
		List<Airport> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Airport a =new Airport(res.getInt("Airport_ID"), res.getString("name"), res.getString("city"),
						res.getString("country"), res.getString("IATA_FAA"), res.getString("ICAO"),
						res.getDouble("Latitude"), res.getDouble("Longitude"), res.getFloat("timezone"),
						res.getString("dst"), res.getString("tz"));
				list.add(a);
				idMap.put(a.getAirportId(), a);
				
				
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public List<LatLon> getCollegamento(Map<Integer, Airport> idMap) {
		String sql = "SELECT a1.airport_id as a1id, a1.Latitude as lat1, a1.Longitude as lon1, a2.airport_id as a2id, a2.Latitude as lat2, a2.Longitude as lon2 " + 
				"FROM airport a1, airport a2, route r " + 
				"where a1.airport_id = r.source_airport_id " + 
				"and a2.airport_id = r.destination_airport_id ";
		List<LatLon> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while(res.next()) {
				
				LatLon latlon = new LatLon(res.getDouble("lat1"), res.getDouble("lon1"),
										   res.getDouble("lat2"), res.getDouble("lon2"),
										   idMap.get(res.getInt("a1id")),
										   idMap.get(res.getInt("a2id")));
				result.add(latlon);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	public Integer getFiumicino() {
		String sql = "select airport_id " + 
				"from airport " + 
				"where name = 'Fiumicino'";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			Integer id = null;
			if(res.next()) {
				
				id = res.getInt("airport_id");
			}
			conn.close();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public Double getDistanza(Airport a1, Airport a2) {
		String sql = "SELECT a1.Latitude as lat1, a1.Longitude as lon1, a2.Latitude as lat2, a2.Longitude as lon2 " + 
				"FROM airport a1, airport a2 " + 
				"where a1.airport_id = ? " + 
				"and a2.airport_id = ? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getAirportId());
			st.setInt(2, a2.getAirportId());
			ResultSet res = st.executeQuery();
			
			if(res.next()) {
				
				Double distanza = LatLngTool.distance(
						new LatLng(res.getDouble("lat1"),res.getDouble("lon1")),
						new LatLng(res.getDouble("lat2"),res.getDouble("lon2")),
						LengthUnit.KILOMETER);
				conn.close();
				return distanza;
			}
			conn.close();
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


}
