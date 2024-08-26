package com.asti.departments.client;

import com.asti.departments.dto.EmployeeDto;
import com.asti.departments.exception.EmployeeServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceClient {

  private final RestTemplate restTemplate;

  public List<EmployeeDto> getEmployeesByDepartmentId(String departmentId) {
    HttpEntity<String> entity = generateHttpEntity();

    try {
      log.info("Getting employees by departmentId: {}", departmentId);
      ResponseEntity<List<EmployeeDto>> response =
          restTemplate.exchange(
              "http://localhost:8080/users/department/" + departmentId,
              HttpMethod.GET,
              entity,
              new ParameterizedTypeReference<>() {});

      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException ex) {
      log.error(ex.getResponseBodyAsString());
      throw new EmployeeServiceException("Error fetching employees: " + ex.getMessage(), ex);
    } catch (RestClientException ex) {
      log.error(ex.getMessage());
      throw new EmployeeServiceException("Error fetching employees: " + ex.getMessage(), ex);
    }
  }

  private HttpEntity<String> generateHttpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(getCurrentToken());

    return new HttpEntity<>(headers);
  }

  private String getCurrentToken() {
    final var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof final JwtAuthenticationToken jwtToken) {
      return jwtToken.getToken().getTokenValue();
    } else {
      throw new IllegalStateException("Authentication is not of type JwtAuthenticationToken");
    }
  }
}
