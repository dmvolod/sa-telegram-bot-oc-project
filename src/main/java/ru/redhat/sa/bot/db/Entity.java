package ru.redhat.sa.bot.db;

import java.util.Date;
import java.util.UUID;

public class Entity {
	private final String STATUS_ACTIVE = "Active".toUpperCase();
	
	private UUID uuid = UUID.randomUUID();
	private java.sql.Timestamp dateTime = new java.sql.Timestamp((new Date()).getTime());
	private String version;
	private String status = STATUS_ACTIVE;
	private String classifier;
	private String color;
	private String morphology;
	private String userKey;
	private String userKeyRef;
	private String body;
}
