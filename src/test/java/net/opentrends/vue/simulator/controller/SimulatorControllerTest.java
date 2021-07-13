package net.opentrends.vue.simulator.controller;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.opentrends.vue.simulator.dto.CassetteTypeTO;
import net.opentrends.vue.simulator.dto.CoeficientsTO;
import net.opentrends.vue.simulator.dto.ConfigReaderTO;
import net.opentrends.vue.simulator.dto.DateTimeTO;
import net.opentrends.vue.simulator.dto.DeviceStatusTO;
import net.opentrends.vue.simulator.dto.ErrorTO;
import net.opentrends.vue.simulator.dto.EthernetTO;
import net.opentrends.vue.simulator.dto.ImagesTO;
import net.opentrends.vue.simulator.dto.ProcessResultTO;
import net.opentrends.vue.simulator.dto.ReaderDataTO;
import net.opentrends.vue.simulator.dto.ResultTO;
import net.opentrends.vue.simulator.dto.StatusTO;
import net.opentrends.vue.simulator.dto.WarningTO;
import net.opentrends.vue.simulator.dto.WifiApTO;
import net.opentrends.vue.simulator.dto.WifiModeTO;
import net.opentrends.vue.simulator.dto.WifiStationTO;
import net.opentrends.vue.simulator.service.SimulatorService;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class SimulatorControllerTest extends BaseResourceDocumentedTest {
	
	private SimulatorService simulatorService;
	private SimulatorController simulatorController;
	
	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		simulatorService = mock(SimulatorService.class);
		simulatorController = new SimulatorController(simulatorService);
		
		configureForDocumentation(restDocumentation, simulatorController);
	}
	
	@Test
	public void should_return_a_valid_configuration() throws Exception {
		when(simulatorService.getConfigReader(any(), any())).thenReturn(createConfigReaderToMock());
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/vue/simulator/{serialNumber}/v2.4/config_reader", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("Config").description("Reader configuration"),
                        	fieldWithPath("Config.ethernet").description("Ethernet information"),
                        	fieldWithPath("Config.ethernet.dhcp").description("Ethernet dhcp"),
                        	fieldWithPath("Config.ethernet.gateway").description("Ethernet gateway"),
                        	fieldWithPath("Config.ethernet.ip").description("Ethernet ip"),
                        	fieldWithPath("Config.ethernet.netmask").description("Ethernet netmask"),
                        	fieldWithPath("Config.wifi_ap").description("Wi-fi ap information"),
                        	fieldWithPath("Config.wifi_ap.ip").description("Wi-fi ap ip"),
                        	fieldWithPath("Config.wifi_ap.netmask").description("Wi-fi ap netmask"),
                        	fieldWithPath("Config.wifi_ap.pwd").description("Wi-fi ap pwd"),
                        	fieldWithPath("Config.wifi_ap.ssid").description("Wi-fi ap ssid"),
                        	fieldWithPath("Config.wifi_mode").description("Wifi_mode information"),
                        	fieldWithPath("Config.wifi_mode.wifi_ap").description("Wifi_mode wifi_ap"),
                        	fieldWithPath("Config.wifi_station").description("Wifi_station information"),                        	
                        	fieldWithPath("Config.wifi_station.dhcp").description("Wifi_station dhcp"),
                        	fieldWithPath("Config.wifi_station.gateway").description("Wifi_station gateway"),
                        	fieldWithPath("Config.wifi_station.ip").description("Wifi_station ip"),
                        	fieldWithPath("Config.wifi_station.netmask").description("Wifi_station netmask"),
                        	fieldWithPath("Config.wifi_station.pwd").description("Wifi_station pwd"),
                        	fieldWithPath("Config.wifi_station.ssid").description("Wifi_station ssid")                        	
                        )
                ));
	}
	
	@Test
	public void should_return_a_cancel_timed_scan() throws Exception {
		when(simulatorService.cancelTimedScan(any())).thenReturn("cancel");
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.put("/api/vue/simulator/{serialNumber}/v2.4/cancel_timed_scan", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("dummy").description("Dummy response")
                        )
                ));
	}
	
	@Test
	public void should_return_a_image() throws Exception {
		when(simulatorService.getImage(any())).thenReturn(ImagesTO.builder().id(1).image(("e01").getBytes()).build());
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/vue/simulator/{serialNumber}/v2.4/images", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("Image").description("Image information"),
                        	fieldWithPath("Image.id").description("Image id"),
                        	fieldWithPath("Image.image").description("Image byte[]")
                        )
                ));
	}
	
	@Test
	public void should_return_a_reader_date_time() throws Exception {
		DateTimeTO dt = DateTimeTO.builder()
								  .dateTime("date_time")
								  .build();
		when(simulatorService.getReaderDateAndTime(any())).thenReturn(dt);
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/vue/simulator/{serialNumber}/v2.4/reader_date_and_time", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("Timestamp").description("Timestamp information"),
                        	fieldWithPath("Timestamp.date_time").description("Timestamp date_time")
                        )
                ));
	}
	
	@Test
	public void should_return_a_reader_status() throws Exception {
		when(simulatorService.getStatusConfig(any())).thenReturn(createStatusToMock());
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/vue/simulator/{serialNumber}/status", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("Status").description("Status information"),
                        	fieldWithPath("Status.device_status").description("Status device status information"),
                        	fieldWithPath("Status.device_status.busy_state").description("Device status busy_state"),
                        	fieldWithPath("Status.device_status.cassette_in").description("Device status cassette_in"),
                        	fieldWithPath("Status.device_status.cassette_time").description("Device status cassette_time"),
                        	fieldWithPath("Status.device_status.date_time").description("Device status date time"),
                        	fieldWithPath("Status.device_status.db_version").description("Device status db version"),
                        	fieldWithPath("Status.device_status.platform").description("Device status platform"),
                        	fieldWithPath("Status.device_status.release_version").description("Device release version"),
                        	fieldWithPath("Status.device_status.serial_number").description("Device status serial number"),
                        	fieldWithPath("Status.device_status.settings_version").description("Device status settings version"),
                        	fieldWithPath("Status.error").description("Error information"),
                        	fieldWithPath("Status.error.code").description("Error code"),
                        	fieldWithPath("Status.error.description").description("Error description")
                        )
                ));
	}
	
	@Test
	public void should_return_cassette_processes() throws Exception {
		when(simulatorService.getCassetteProcesses(any())).thenReturn(createResultToMock());
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/vue/simulator/{serialNumber}/v2.4/cassette_processes", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("Result").description("Result information"),
                        	fieldWithPath("Result.cassette_process_id").description("Result device status information"),
                        	fieldWithPath("Result.cassette_type").description("Cassette type information"),
                        	fieldWithPath("Result.cassette_type.description").description("Cassette type description"),
                        	fieldWithPath("Result.cassette_type.code").description("Cassette type code"),
                        	fieldWithPath("Result.error").description("Error information"),
                        	fieldWithPath("Result.error.code").description("Error code"),
                        	fieldWithPath("Result.error.description").description("Error description"),
                        	fieldWithPath("Result.previous_process_id").description("Result previous_process_id"),                        	
                        	fieldWithPath("Result.reader_data.date_time").description("Result reader date time"),
                        	fieldWithPath("Result.reader_data.cassette_time").description("Result cassette_time"),
                        	fieldWithPath("Result.reader_data.db_version").description("Result db version"),
                        	fieldWithPath("Result.reader_data.release_version").description("Result reader release version"),
                        	fieldWithPath("Result.reader_data.settings_version").description("Result status settings version"),                        	
                        	fieldWithPath("Result.results[].background").description("Result background"),
                        	fieldWithPath("Result.results[].color_01").description("Result color 01"),
                        	fieldWithPath("Result.results[].color_02").description("Result color 02"),
                        	fieldWithPath("Result.results[].control").description("Result control"),
                        	fieldWithPath("Result.results[].error").description("Error information"),
                        	fieldWithPath("Result.results[].error.code").description("Error code"),
                        	fieldWithPath("Result.results[].error.description").description("Error description"),
                        	fieldWithPath("Result.results[].initial").description("Result initial"),
                        	fieldWithPath("Result.results[].noise").description("Result noise"),
                        	fieldWithPath("Result.results[].positive").description("Result positive"),
                        	fieldWithPath("Result.results[].reliable").description("Result negative"),
                        	fieldWithPath("Result.results[].position").description("Result position"),                        	
                        	fieldWithPath("Result.results[].test_line_value").description("Result test line value"),
                        	fieldWithPath("Result.results[].test_name").description("Result test name"),
                        	fieldWithPath("Result.results[].scaledResult").description("Result scaled result"),
                        	fieldWithPath("Result.results[].lotNumber").description("Result lot number"),                        	
                        	fieldWithPath("Result.results[].coeficients.a").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.b").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.c").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.d").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.f").description("Result coeficients"),                        	
                        	fieldWithPath("Result.results[].warnings[].code").description("Warning code"),
                        	fieldWithPath("Result.results[].warnings[].description").description("Warning description")
                        )
                ));
	}
	
	@Test
	public void should_return_cassette_processes_post() throws Exception {
		when(simulatorService.getCassetteProcesses2(any())).thenReturn(createResultToMock());
		
		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/vue/simulator/{serialNumber}/v2.4/cassette_processes", "serialNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(this.documentationHandler.document(
                		pathParameters(
                				parameterWithName("serialNumber").description("VUE Reader serial number")
                        ),
                		responseFields(
                        	fieldWithPath("Result").description("Result information"),
                        	fieldWithPath("Result.cassette_process_id").description("Result device status information"),
                        	fieldWithPath("Result.cassette_type").description("Cassette type information"),
                        	fieldWithPath("Result.cassette_type.description").description("Cassette type description"),
                        	fieldWithPath("Result.cassette_type.code").description("Cassette type code"),
                        	fieldWithPath("Result.error").description("Error information"),
                        	fieldWithPath("Result.error.code").description("Error code"),
                        	fieldWithPath("Result.error.description").description("Error description"),
                        	fieldWithPath("Result.previous_process_id").description("Result previous_process_id"),                        	
                        	fieldWithPath("Result.reader_data.date_time").description("Result reader date time"),
                        	fieldWithPath("Result.reader_data.cassette_time").description("Result cassette_time"),
                        	fieldWithPath("Result.reader_data.db_version").description("Result db version"),
                        	fieldWithPath("Result.reader_data.release_version").description("Result reader release version"),
                        	fieldWithPath("Result.reader_data.settings_version").description("Result status settings version"),                        	
                        	fieldWithPath("Result.results[].background").description("Result background"),
                        	fieldWithPath("Result.results[].color_01").description("Result color 01"),
                        	fieldWithPath("Result.results[].color_02").description("Result color 02"),
                        	fieldWithPath("Result.results[].control").description("Result control"),
                        	fieldWithPath("Result.results[].error").description("Error information"),
                        	fieldWithPath("Result.results[].error.code").description("Error code"),
                        	fieldWithPath("Result.results[].error.description").description("Error description"),
                        	fieldWithPath("Result.results[].initial").description("Result initial"),
                        	fieldWithPath("Result.results[].noise").description("Result noise"),
                        	fieldWithPath("Result.results[].positive").description("Result positive"),
                        	fieldWithPath("Result.results[].reliable").description("Result negative"),
                        	fieldWithPath("Result.results[].position").description("Result position"),                        	
                        	fieldWithPath("Result.results[].test_line_value").description("Result test line value"),
                        	fieldWithPath("Result.results[].test_name").description("Result test name"),
                        	fieldWithPath("Result.results[].scaledResult").description("Result scaled result"),
                        	fieldWithPath("Result.results[].lotNumber").description("Result lot number"),                        	
                        	fieldWithPath("Result.results[].coeficients.a").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.b").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.c").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.d").description("Result coeficients"),
                        	fieldWithPath("Result.results[].coeficients.f").description("Result coeficients"),                        	
                        	fieldWithPath("Result.results[].warnings[].code").description("Warning code"),
                        	fieldWithPath("Result.results[].warnings[].description").description("Warning description")
                        )
                ));
	}
	
	
	
	private StatusTO createStatusToMock() {
		return StatusTO.builder()
			.deviceStatus(DeviceStatusTO.builder()
				.busyState(Boolean.TRUE)
				.cassetteIn(Boolean.TRUE)
				.cassetteTime(1.0d)
				.dateTime("date_time")
				.dbVersion(1)
				.platform("platform")
				.releaseVersion("releaseVersion")
				.serialNumber("serialNumber")
				.settingsVersion("settingsVersion")
				.build())
			.error(ErrorTO.builder().code(1).description("description").build())
			.build();
	}
	
	private ConfigReaderTO createConfigReaderToMock() {
		return ConfigReaderTO.builder()
				.ethernetTO(EthernetTO.builder()
						  .dhcp(Boolean.TRUE)
						  .gateway("gate")
						  .ip("ip")
						  .netmask("netmask")
						  .build())
				.wifiApTO(WifiApTO.builder()
						  		  .ip("ip")
						  		  .netmask("netmask")
						  		  .pwd("pwd")
						  		  .Ssid("Ssid")
						  		  .build())
				.wifimodeTO(WifiModeTO.builder()
						.wifiAp(Boolean.FALSE)
						.build())
				.wifiStationTO(WifiStationTO.builder()
						.dhcp(Boolean.FALSE)
						.gateway("gateway")
						.ip("ip")
						.netmask("netmask")
						.pwd("pwd")
						.ssid("ssid")
						.build())
				.build();
	}
	
	private ResultTO createResultToMock() {
		CassetteTypeTO ct = CassetteTypeTO.builder()
										  .code(1)
										  .type("type")
										  .build();
		CoeficientsTO coeficients = CoeficientsTO.builder()
										.a(1)
										.b(1)
										.c(1)
										.d(1)
										.f(1).build();
		ProcessResultTO pr = ProcessResultTO.builder()
											.background("background")
											.coeficients(coeficients)
											.color01("color01")
											.color02("color02")
											.control(1.0)
											.error(ErrorTO.builder().code(1).description("description").build())
											.initial('C')
											.lotNumber(123)
											.noise(1.0)
											.position(1)
											.positive(Boolean.TRUE)
											.reliable(Boolean.TRUE)
											.scaledResult(2.0d)
											.testLineValue(10.1d)
											.testName("testName")
											.noise(1.0)
											.warnings(asList(WarningTO.builder().code(11).description("description").build()))
											.build();
		ReaderDataTO rd = ReaderDataTO.builder()
									  .cassetteTime(2.0d)
									  .dateTime("dateTime")
									  .dbVersion("dbVersion")
									  .releaseVersion("releaseVersion")
									  .settingsVersion("settingsVersion")
									  .build();
		return ResultTO.builder()
				.cassetteProcessId(1111)
				.cassetteType(ct)
				.error(ErrorTO.builder().code(1).description("description").build())
				.previousProcessId(2222)
				.processResults(asList(pr))
				.readerData(rd)
				.build();
	}
}
