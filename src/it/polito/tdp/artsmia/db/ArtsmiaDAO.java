package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects( Map<Integer,ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				if(idMap.get("Object_id")==null)//se è nullo vuol dire che nella mappa non ce allora lo devo creare
				{
					//righe di codice per creazione dell'oggetto artObject
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
					
				idMap.put(artObj.getId(), artObj);//e poi lo metto nella mappa così successivamente non andro a ricrearlo
				
				result.add(artObj);
			}else {
				result.add(idMap.get("Object_id"));//se no aggiungo l'oggetto che ripreso dalla mappa
				
			}}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;}
		
		return result;
	}
	
	public List<Adiacenza> listAdiacenze(){
		
		//non mi serve la mappa perhcè mi baso tutto sugli gli id
		
		String sql="SELECT e1.object_id AS o1, e2.object_id AS o2  , COUNT(*) AS cnt " + 
				"FROM exhibition_objects AS e1 , exhibition_objects AS e2 " + 
				"WHERE e1.exhibition_id=e2.exhibition_id  " + 
				"AND e2.object_id > e1.object_id " + 
				"GROUP BY e1.object_id , e2.object_id " + 
				"";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
			
				Adiacenza adiacenza = new Adiacenza(res.getInt("o1"), res.getInt("o2"), res.getInt("cnt"));
					
				result.add(adiacenza);
			
			}
			conn.close();
			return result;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;}
		
		
	}
		
		
		
		
	}
		
	
	
