package neo.ehsanodyssey.library.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import neo.ehsanodyssey.library.dal.model.User;
import neo.ehsanodyssey.library.dal.repository.UserRepository;
import neo.ehsanodyssey.library.exception.UserNotFoundException;
import neo.ehsanodyssey.library.service.UserManagementService;
import neo.ehsanodyssey.library.service.dto.UserDto;
import neo.ehsanodyssey.library.service.mapper.UserMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
@CacheConfig(cacheNames="usersCache")
@RequiredArgsConstructor
public class DefaultUserManagementService implements UserManagementService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BiFunction<User, UserDto, User> updateModelFromDTO = this::updateModelWithExpectedDTOValues;
    private final Function<User, UserDto> updateAndReturnDTO = this::updateAndThenMapToDTO;
    private final BiFunction<User, UserDto, UserDto> updateFunction = updateModelFromDTO.andThen(updateAndReturnDTO);

    @Override
    @Cacheable(key = "'user_' + #id")
    public UserDto getUser(String id) {
        return this.userRepository
                .findById(id)
                .map(this.userMapper::toDto)
                .orElseThrow(() -> this.createNotFoundException(id));
    }

    @Override
    @Cacheable
    public List<UserDto> getUsers() {
        var users = this.userRepository.findAll();
        return StreamSupport
                .stream(users.spliterator(), false)
                .map(this.userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @CachePut
    public UserDto createUser(UserDto dto) {
        if (dto.getId() != null) {
            dto.setId(null);
        }
        return this.userMapper.toDto(
                this.userRepository.save(
                        this.userMapper.toModel(dto)
                )
        );
    }

    @Override
    @CachePut
    public UserDto updateUser(UserDto dto) {
        return this.userRepository
                .findById(dto.getId())
                .map(model -> this.updateFunction.apply(model, dto))
                .orElseThrow(() -> this.createNotFoundException(dto.getId()));
    }

    @Override
    @CacheEvict(key = "'user_' + #id")
    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }

    private User updateModelWithExpectedDTOValues(User model, UserDto dto) {
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        return model;
    }

    private UserDto updateAndThenMapToDTO(User model) {
        return this.userMapper.toDto(
                this.userRepository.save(model)
        );
    }

    private UserNotFoundException createNotFoundException(String id) {
        return new UserNotFoundException(
                String.format("user with id '%s' not found", id)
        );
    }
}
