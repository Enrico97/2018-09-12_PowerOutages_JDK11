package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Interruzioni;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	Map<Integer, Nerc> nerc = new HashMap<>();
	Map<Integer, Interruzioni> mappaInterruzioni = new HashMap<>();
	
	public List<Nerc> loadAllNercs() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
				nerc.put(res.getInt("id"), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<Adiacenza> archi() {

		this.loadAllNercs();
		String sql = "select distinct n.nerc_one, n.nerc_two, COUNT(distinct MONTH(po1.date_event_began),YEAR(po1.date_event_began)) as peso from nercRelations as n, powerOutages as po1, powerOutages as po2 where po1.nerc_id=n.nerc_one and po2.nerc_id=n.nerc_two and YEAR(po1.date_event_began)=YEAR(po2.date_event_began) and MONTH(po1.date_event_began)=MONTH(po2.date_event_began) group by n.nerc_one, n.nerc_two";
		List<Adiacenza> archi = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza n = new Adiacenza(nerc.get(res.getInt("n.nerc_one")), nerc.get(res.getInt("n.nerc_two")), res.getInt("peso"));
				archi.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return archi;
	}
	
	public List<Interruzioni> interruzioni() {

		this.loadAllNercs();
		String sql = "select id, nerc_id, date_event_began, date_event_finished from powerOutages";
		List<Interruzioni> interruzioni = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Interruzioni n = new Interruzioni(res.getInt("id"), nerc.get(res.getInt("nerc_id")), res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime());
				interruzioni.add(n);
				mappaInterruzioni.put(res.getInt("id"), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return interruzioni;
	}
	
	public Map<Integer, Interruzioni> mappaInterruzioni() {

		this.loadAllNercs();
		String sql = "select id, nerc_id, date_event_began, date_event_finished from powerOutages";
		List<Interruzioni> interruzioni = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Interruzioni n = new Interruzioni(res.getInt("id"), nerc.get(res.getInt("nerc_id")), res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime());
				interruzioni.add(n);
				mappaInterruzioni.put(res.getInt("id"), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return mappaInterruzioni;
	}
}
