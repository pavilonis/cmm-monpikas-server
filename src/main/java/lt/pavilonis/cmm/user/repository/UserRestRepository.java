package lt.pavilonis.cmm.user.repository;

import lt.pavilonis.cmm.common.EntityRepository;
import lt.pavilonis.cmm.user.domain.PresenceTimeRepresentation;
import lt.pavilonis.cmm.user.domain.UserRepresentation;
import lt.pavilonis.cmm.user.ui.UserFilter;
import lt.pavilonis.util.TimeUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRestRepository implements EntityRepository<UserRepresentation, String, UserFilter> {

   private static final Logger LOG = LoggerFactory.getLogger(UserRestRepository.class);
   private static final String SEGMENT_USERS = "users";
   private static final String SEGMENT_SCANLOG = "scanlog";
   private static final String SCANNER_ID_CANTEEN = "6";
   private static final String SEGMENT_PRESENCE = "presence";

   @Value("${api.path}")
   private String apiPath;

   @Autowired
   private RestTemplate restTemplate;

   @Override
   public List<UserRepresentation> load(UserFilter filter) {
      LocalDateTime opStart = LocalDateTime.now();
      MultiValueMap<String, String> params = new LinkedMultiValueMap<>(3);

      addParam(params, "name", filter.getName());
      addParam(params, "role", filter.getRole());
      addParam(params, "group", filter.getGroup());

      UserRepresentation[] response = restTemplate.getForObject(uri(params, SEGMENT_USERS), UserRepresentation[].class);

      LOG.info("All users loaded [number={}, duration={}]", response.length, TimeUtils.duration(opStart));
      return Arrays.asList(response);
   }

   private void addParam(MultiValueMap<String, String> params, String paramName, String paramValue) {
      if (StringUtils.isNoneBlank(paramValue)) {
         params.set(paramName, paramValue);
      }
   }

   @Override
   public Optional<UserRepresentation> find(String cardCode) {

      LocalDateTime opStart = LocalDateTime.now();

      UserRepresentation result = restTemplate
            .getForObject(uri(SEGMENT_USERS, cardCode), UserRepresentation.class);

      LOG.info("User loaded [cardCode={}, duration={}]", cardCode, TimeUtils.duration(opStart));
      return Optional.ofNullable(result);
   }

   public void delete(String cardCode) {
      restTemplate.delete(uri(SEGMENT_USERS, cardCode));
   }

   @Override
   public Class<UserRepresentation> getEntityClass() {
      return UserRepresentation.class;
   }

   public UserRepresentation update(UserRepresentation userRepresentation) {
      ResponseEntity<UserRepresentation> response = restTemplate.exchange(
            uri(SEGMENT_USERS),
            HttpMethod.PUT,
            new HttpEntity<>(userRepresentation),
            UserRepresentation.class
      );
      return response.getBody();
   }

   private URI uri(String... segments) {
      return uri(new LinkedMultiValueMap<>(0), segments);
   }

   private URI uri(MultiValueMap<String, String> params, String... segments) {
      return UriComponentsBuilder
            .fromUriString(apiPath)
            .pathSegment(segments)
            .queryParams(params)
            .build()
            .toUri();
   }

   public List<PresenceTimeRepresentation> loadPresenceTime(String cardCode) {
      PresenceTimeRepresentation[] response = restTemplate
            .getForObject(uri(SEGMENT_PRESENCE, cardCode), PresenceTimeRepresentation[].class);
      return Arrays.asList(response);
   }

   public void logUserScan(String cardCode) {
      LOG.info("Sending scanLog post request");
      try {
         restTemplate.postForObject(uri(SEGMENT_SCANLOG, SCANNER_ID_CANTEEN, cardCode), null, Void.class);
      } catch (HttpClientErrorException e) {
         LOG.error("Error writing log for user with card: " + cardCode + ". Http status: " + e.getStatusCode());
      }
   }

   @Override
   public UserRepresentation saveOrUpdate(UserRepresentation entity) {
      throw new NotImplementedException("Not needed yet");
   }
}