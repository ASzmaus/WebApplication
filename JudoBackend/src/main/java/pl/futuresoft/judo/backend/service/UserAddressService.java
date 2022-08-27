package pl.futuresoft.judo.backend.service;

import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.UserAddressDto;
import pl.futuresoft.judo.backend.entity.UserAddress;
import pl.futuresoft.judo.backend.exception.EntityNotFoundException;
import pl.futuresoft.judo.backend.repository.UserAddressRepository;
import pl.futuresoft.judo.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserAddressService {

	@Autowired
	UserAddressRepository userAddressRepository;

	@Autowired
	UserRepository userRepository;

	public void writeDtoToEntity(UserAddress userAddress, UserAddressDto userAddressDto ) {
		userAddress.setUserAddressId(userAddressDto.getUserAddressId());
		userAddress.setStreet(userAddressDto.getStreet());
		userAddress.setHouseNumber(userAddressDto.getHouseNumber());
		userAddress.setCity(userAddressDto.getCity());
		userAddress.setPostcode(userAddressDto.getPostcode());
		userAddress.setUserId(userAddressDto.getUserId());
	}

	public void writeEntityToDto(UserAddress userAddress, UserAddressDto userAddressDto) {
		userAddressDto.setUserAddressId(userAddress.getUserAddressId());
		userAddressDto.setStreet(userAddress.getStreet());
		userAddressDto.setHouseNumber(userAddress.getHouseNumber());
		userAddressDto.setCity(userAddress.getCity());
		userAddressDto.setPostcode(userAddress.getPostcode());
		userAddressDto.setUserId(userAddress.getUserId());
	}

	@Transactional
	public UserAddressDto addUserAddress(UserAddressDto userAddressDto){//, Integer userId) {
		Preconditions.checkNotNull(userAddressDto.getUserId(), "UserId can not be null");
		if (!userRepository.findById(userAddressDto.getUserId()).isPresent())
			throw new EntityNotFoundException();
		UserAddress userAddress = new UserAddress();
		writeDtoToEntity(userAddress, userAddressDto);
		userAddressRepository.save(userAddress);
		userAddressDto.setUserAddressId(userAddress.getUserAddressId());
		return userAddressDto;
	}

	@Transactional
	public UserAddressDto editUserAddress(UserAddressDto userAddressDto) {
		if (!userAddressRepository.findById(userAddressDto.getUserAddressId()).isPresent()) {
			throw new EntityNotFoundException();
		} else {
			UserAddress userAddress = userAddressRepository.findById(userAddressDto.getUserAddressId()).get();
			userAddress.setStreet(userAddressDto.getStreet());
			userAddress.setHouseNumber(userAddressDto.getHouseNumber());
			userAddress.setCity(userAddressDto.getCity());
			userAddress.setPostcode(userAddressDto.getPostcode());
			userAddress.setUserId(userAddressDto.getUserId());
			writeEntityToDto(userAddress, userAddressDto);
			userAddressRepository.save(userAddress);
			return userAddressDto;
		}
	}

	@Transactional
	public List<UserAddressDto> userAddressList(){
		Iterable<UserAddress> iterableUserAddress = userAddressRepository.findAll();
		if (iterableUserAddress==null)
			return null;
		List<UserAddressDto> listUserAddressDto=StreamSupport
				 .stream(iterableUserAddress.spliterator(), false)
				 .map(p->{
					 UserAddressDto userAddressDto = new UserAddressDto();
				writeEntityToDto(p, userAddressDto);
				return userAddressDto;})
				 .collect(Collectors.toList());
			return listUserAddressDto;
	}
}
