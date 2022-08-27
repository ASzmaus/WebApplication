package pl.futuresoft.judo.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.configuration.MailConfiguration;
import pl.futuresoft.judo.backend.dto.UserDto;
import pl.futuresoft.judo.backend.entity.User;
import pl.futuresoft.judo.backend.exception.*;
import pl.futuresoft.judo.backend.jwt.Userdetails;
import pl.futuresoft.judo.backend.repository.ClubRepository;
import pl.futuresoft.judo.backend.repository.UserRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired
	RoleService roleService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	MailService mailService;

	@Autowired
	MailConfiguration mailConfiguration;

	public void writeEntityToDto(User user, UserDto userDto) {
		userDto.setActive(user.isActive());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setRole(roleService.getRoleById(user.getRoleId()));
		userDto.setUserId(user.getUserId());
		userDto.setClubId(user.getClubId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setPosition(user.getPosition());
		userDto.setBirthdate(user.getBirthdate());
	}
	public void writeEntityToDtoPublic(User userDb, UserDto userDto)
	{
		userDto.setEmail(userDb.getEmail());
	}

	@Transactional
	public UserDto getUserByEmail(String email)
	{
		User userDb = userRepository.findByEmailAndActive(email, true);
		if (userDb==null)
			return null;
		UserDto dto = new UserDto();
		writeEntityToDto(userDb, dto);
		return dto;
	}

	public UserDto getLoggedUser()
	{
		if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken)
		{
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)
					SecurityContextHolder.getContext().getAuthentication();
			if (usernamePasswordAuthenticationToken.getPrincipal() instanceof Userdetails)
			{
				Userdetails ud = (Userdetails) usernamePasswordAuthenticationToken.getPrincipal();
				return ud.getUser();
			}

		}
		return null;
	}

	@Transactional
	public UserDto activate(String token) throws TokenExpiredException, TokenNotFoundException
	{
		List<User> users = userRepository.findByActivationToken(token);
		if (users.size()==1)
		{
			User u = users.get(0);
			if (u.getActivationTokenExpirationDate().before(new Date()))
				throw new TokenExpiredException();
			u.setActivationToken(null);
			u.setActivationTokenExpirationDate(null);
			u.setActive(true);
			userRepository.save(u);
			UserDto udto = new UserDto();
			writeEntityToDtoPublic(u, udto);
			return udto;
		}
		else throw new TokenNotFoundException();
	}

	@Transactional
	public UserDto register(UserDto userDto) throws LoginIsUsedException, UnsupportedEncodingException, MessagingException {
		UserDto user = getUserByEmail(userDto.getEmail());
		if (user!=null)
		{
			throw new LoginIsUsedException();
		}
		else
		{
			User userDb = new User();
			userDb.setActive(false);
			userDb.setEmail(userDto.getEmail());
			userDb.setRoleId("p");
			userDb.setDeleted(false);
			userDb.setPassword(bcryptPasswordEncoder.encode(userDto.getPassword()));

			String token = "";
			Random r = new Random();
			String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLOMNOPQRSTUVWZYX0123456789";
			for(int i=0;i<64;i++)
				token+=allowed.charAt(r.nextInt(allowed.length()));
			userDb.setActivationToken(token);
			Calendar c= Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 1);
			userDb.setActivationTokenExpirationDate( new Timestamp( c.getTime().getTime()));
			userRepository.save(userDb);
			userDto.setUserId(userDb.getUserId());
			String externalUrl = mailConfiguration.getExternalUrl();
			String message="<html>"
					+ "<head>\r\n" +
					"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
					+ "</head>"
					+ "<body>"
					+ "<p>A new account has been created on the Sports Clubs website.</p>"
					+ "<p>Click on <a href=\""+externalUrl+"/activate/"+token+"\">link</a> to activate your account.</p>"
					+ "</body></html>";
			mailService.confSmtpHostAndSendEmail(userDto.getEmail(),"Activation of the account on the Sports Clubs website", message);
			return userDto;
		}
	}

	@Transactional
	public UserDto remindPassword(UserDto dto) throws UnsupportedEncodingException, MessagingException, LoginNotFoundException {
	
		UserDto user = getUserByEmail(dto.getEmail());
		if (user==null)
		{
			throw new LoginNotFoundException();
		}
		else
		{
			User userDb =  userRepository.findByEmailAndActive(dto.getEmail(),true);

			String token = "";
			Random r = new Random();
			String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLOMNOPQRSTUVWZYX0123456789";
			for(int i=0;i<64;i++)
				token+=allowed.charAt(r.nextInt(allowed.length()));
			userDb.setReminderToken(token);
			Calendar c= Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 1);
			userDb.setReminderTokenExpirationDate( new Timestamp( c.getTime().getTime()));
			userRepository.save(userDb);
			String externalUrl = mailConfiguration.getExternalUrl();

			String message="<html>"
					+ "<head>\r\n" +
					"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
					+ "</head>"
					+ "<body>"
					+ "<p>An attempt has been made to recover the account password on the Sports Clubs website.</p>"
					+ "<p>Click on <a href=\""+ externalUrl+"/remainderEnterPassword/"+token+"\">link</a> to regain access to your account.</p>"
					+ "</body></html>";
			mailService.confSmtpHostAndSendEmail(user.getEmail(),"Regaining access to your Sports Clubs account", message);
			return user;
		}

	}
	@Transactional
	public UserDto setNewPassword(String token, UserDto userDto) throws TokenExpiredException, TokenNotFoundException {
		
		List<User> users = userRepository.findByReminderToken(token);
		if (users.size()==1)
		{
			User u = users.get(0);
			if (u.getReminderTokenExpirationDate().before(new Date()))
				throw new TokenExpiredException();
			u.setReminderToken(null);
			u.setReminderTokenExpirationDate(null);
			u.setActive(true);
			u.setPassword(bcryptPasswordEncoder.encode(userDto.getPassword()));
			userRepository.save(u);
			UserDto udto = new UserDto();
			writeEntityToDtoPublic(u, udto);
			return udto;
		}
		else throw new TokenNotFoundException();
	}

	public UserDto getUser(Integer clubId, Integer userId)
	{
		Optional<User> userDb = userRepository.findById(userId);
		if (!userDb.isPresent())
			throw new ObjectNotFoundException("User", userId.toString());
		UserDto dto = new UserDto();
		writeEntityToDto(userDb.get(), dto);
		dto.setPassword(null);
		return dto;
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_o')")
	public UserDto addAdmin(UserDto adminDto, int clubId) {

		if (!clubRepository.findById(clubId).isPresent())
			throw new EntityNotFoundException();
		UserDto user = getUserByEmail(adminDto.getEmail());
		if (user!=null)
			throw new LoginIsUsedException();
		User admin= new User();
		admin.setActive(true);
		admin.setEmail(adminDto.getEmail());
		admin.setPassword(bcryptPasswordEncoder.encode(adminDto.getPassword()));
		admin.setRoleId("a");
		admin.setClubId(clubId);
		admin.setDeleted(false);
		admin.setFirstName(adminDto.getFirstName());
		admin.setLastName(adminDto.getLastName());
		admin.setPosition(adminDto.getPosition());
		admin.setBirthdate(adminDto.getBirthdate());
		userRepository.save(admin);
		adminDto.setUserId(admin.getUserId());
		return adminDto;
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_o')")
	public List<UserDto> adminList(int clubId){
		if (userRepository.findAllByClubIdAndActiveAndRoleIdInOrderByEmail(clubId,true, Arrays.asList(new String[] {"o", "a"})).isEmpty())
			throw new EntityNotFoundException();
	return userRepository.findAllByClubIdAndActiveAndRoleIdInOrderByEmail(clubId,true, Arrays.asList(new String[] {"o", "a"}))
				.stream()
				.filter(e -> e.getDeleted().equals(false))
				.map(p->{
					UserDto dto = new UserDto();
					writeEntityToDto(p, dto);
					return dto;})
				.collect(Collectors.toList());
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_o')")
	public void softDeleteAdmin(int userId) {
		if (!userRepository.findById(userId).isPresent()) {
			throw new EntityNotFoundException();
		} else {
			userRepository.softDelete(userId, true);
		}
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_o')")
	public void editAdmin(UserDto adminDto, int clubId, int userId) {

		if (!clubRepository.findById(clubId).isPresent())
			throw new EntityNotFoundException();
		if (!userRepository.findById(userId).isPresent())
			throw new EntityNotFoundException();
		User admin= userRepository.findAllByClubIdByUserId(clubId, userId);
		if (admin==null)
			throw new EntityNotFoundException();
		UserDto user = getUserByEmail(adminDto.getEmail());
		if (user!=null && user.getUserId()!=userId)
			throw new LoginIsUsedException();

		admin.setEmail(adminDto.getEmail());
		if (adminDto.getPassword()!=null && adminDto.getPassword().trim().length()>0)
			admin.setPassword(bcryptPasswordEncoder.encode(adminDto.getPassword()));
		userRepository.save(admin);

	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_a')")
	public UserDto addStaffByAdmin( UserDto staffDto, Integer clubId) {
		if (!clubRepository.findById(clubId).isPresent())
			throw new EntityNotFoundException();
		UserDto userDto = getUserByEmail(staffDto.getEmail());
		if (userDto != null)
			throw new LoginIsUsedException();
		User staff = new User();
		staff.setActive(true);
		staff.setEmail(staffDto.getEmail());
		staff.setPassword(bcryptPasswordEncoder.encode(staffDto.getPassword()));
		staff.setRoleId("s");
		staff.setClubId(clubId);
		staff.setDeleted(false);
		staff.setFirstName(staffDto.getFirstName());
		staff.setLastName(staffDto.getLastName());
		staff.setPosition(staffDto.getPosition());
		staff.setBirthdate(staffDto.getBirthdate());
		userRepository.save(staff);
		staffDto.setUserId(staff.getUserId());
		return staffDto;
	}


	@Transactional
	@PreAuthorize("hasAuthority('ROLE_a')")
		public void editStaffByAdmin(UserDto staffDto, int clubId, int userId)  {

			if (!clubRepository.findById(clubId).isPresent())
				throw new EntityNotFoundException();
			if (!userRepository.findById(userId).isPresent())
				throw new EntityNotFoundException();
			User staff= userRepository.findAllByClubIdByUserId(clubId, userId);
			if (staff==null)
				throw new EntityNotFoundException();
			UserDto user = getUserByEmail(staffDto.getEmail());
			if (user!=null && user.getUserId()!=userId)
				throw new LoginIsUsedException();
		staff.setEmail(staffDto.getEmail());
			if (staffDto.getPassword()!=null && staffDto.getPassword().trim().length()>0)
				staff.setPassword(bcryptPasswordEncoder.encode(staffDto.getPassword()));
			userRepository.save(staff);
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_a')")
	public void softDeleteStaffByAdmin(int staffId){
		if(!userRepository.findById(staffId).isPresent())
			throw new EntityNotFoundException();
		else{
			userRepository.softDelete(staffId,true);
		}
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_a')")
	public List<UserDto> staffList(int clubId){
		if (userRepository.findAllByClubIdAndActiveAndRoleIdInOrderByEmail(clubId,true, Arrays.asList(new String[] {"s"})).isEmpty())
			throw new EntityNotFoundException();
		return userRepository.findAllByClubIdAndActiveAndRoleIdInOrderByEmail(clubId,true, Arrays.asList(new String[] {"s"}))
				.stream()
				.filter(e -> e.getDeleted().equals(false))
				.map(p->{
					UserDto dto = new UserDto();
					writeEntityToDto(p, dto);
					return dto;})
				.collect(Collectors.toList());

	}

}
