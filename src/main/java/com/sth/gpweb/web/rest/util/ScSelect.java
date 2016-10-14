package com.sth.gpweb.web.rest.util;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Utility class for sc-select when front-end search for a relationship entity
 * @author Jackson Lima
 */
public class ScSelect<T>{
	
	

	private Results<T> results;
	
	 public ScSelect( /*Query: */ String text, String role, String startPage,
			 		  /*Trackmatches: */ List<T> trackmatches,
			 		 /*Results: */String totalResults, String startIndex, String itemsPerPage
			 		  ) {
		super();
		Query auxQuery = new Query(text, role, startPage);
		Trackmatches<T> auxTrackmatches = new Trackmatches<>(trackmatches); 
		this.results = new Results<>(auxQuery, totalResults, startIndex, itemsPerPage, auxTrackmatches);
		
	}

	public Results<T> getResults() {
		return results;
	}

	public void setResults(Results<T> results) {
		this.results = results;
	}

	public class Results<T>{
	    	
	    	@SerializedName("opensearch:Query")
			private Query query;
			@SerializedName("opensearch:totalResults")
			private String totalResults;
			@SerializedName("opensearch:startIndex")
			private String startIndex;
			@SerializedName("opensearch:itemsPerPage")
			private String itemsPerPage;
			private Trackmatches<T> trackmatches;		

			public Results(Query query, String totalResults, String startIndex, String itemsPerPage, Trackmatches<T> trackmatches) {
				super();
				this.query = query;
				this.totalResults = totalResults;
				this.startIndex = startIndex;
				this.itemsPerPage = itemsPerPage;
				this.trackmatches = trackmatches;
			}

			public Query getQuery() {
				return query;
			}

			public void setQuery(Query query) {
				this.query = query;
			}

			public String getTotalResults() {
				return totalResults;
			}

			public void setTotalResults(String totalResults) {
				this.totalResults = totalResults;
			}

			public String getStartIndex() {
				return startIndex;
			}

			public void setStartIndex(String startIndex) {
				this.startIndex = startIndex;
			}

			public String getItemsPerPage() {
				return itemsPerPage;
			}

			public void setItemsPerPage(String itemsPerPage) {
				this.itemsPerPage = itemsPerPage;
			}

			public Trackmatches<T> getTrackmatches() {
				return trackmatches;
			}

			public void setTrackmatches(Trackmatches<T> trackmatches) {
				this.trackmatches = trackmatches;
			}			  			
	    }
	 
	 public class Trackmatches<T>{
			private List<T> track;
			
			public Trackmatches(List<T> track) {
				super();
				this.track = track;
			}

			public List<T> getTrack() {
				return track;
			}

			public void setTrack(List<T> track) {
				this.track = track;
			}    	    		
		}
	 
	  public class Query{
			@SerializedName("#text")
			private String text;
			private String role;
			private String startPage;
			
			public Query(String text, String role, String startPage) {
				super();
				this.text = text;
				this.role = role;
				this.startPage = startPage;
			}

			public String getText() {
				return text;
			}

			public void setText(String text) {
				this.text = text;
			}

			public String getRole() {
				return role;
			}

			public void setRole(String role) {
				this.role = role;
			}

			public String getStartPage() {
				return startPage;
			}

			public void setStartPage(String startPage) {
				this.startPage = startPage;
			}
		}
	 

}
