package net.wmfs.coalesce.aa.access.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {

	private String host;
	private int port;
	private boolean useHttps;
}
