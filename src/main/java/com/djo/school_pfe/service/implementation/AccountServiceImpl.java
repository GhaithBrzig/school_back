package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.dto.ChangePasswordDto;
import com.djo.school_pfe.dto.ResetPasswordDto;
import com.djo.school_pfe.dto.UpdateProfileDto;
import com.djo.school_pfe.entity.ConfirmationKey;
import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Evaluation;
import com.djo.school_pfe.entity.UserEntity;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.error.NotFoundException;
import com.djo.school_pfe.repository.EleveRepository;
import com.djo.school_pfe.repository.EvaluationRepository;
import com.djo.school_pfe.repository.KeyRepository;
import com.djo.school_pfe.repository.UserRepository;
import com.djo.school_pfe.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EvaluationRepository evaluationRepository;
    @Autowired
    EleveRepository eleveRepository;
    @Autowired
    KeyRepository keyRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String forgetPassword(String emailAddress) {
        UserEntity userEntity = this.getUserByEmailAddress(emailAddress);
        this.checkIfUserHasGeneratedAKey(emailAddress);
        String keyValue = UUID.randomUUID().toString();
        this.generateAndPersistKey(keyValue, emailAddress);
        return "We have sent an email to reset your password";
    }

    @Override
    public String resetPassword(ResetPasswordDto resetPasswordDto) {
        this.validateResetPassword(resetPasswordDto);
        UserEntity userEntity = this.userRepository.findByEmailAddress(this.keyRepository.getEmailAddress(resetPasswordDto.getConfirmationKeyValue())).get();
        userEntity.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        this.userRepository.save(userEntity);
        ConfirmationKey confirmationKey = this.keyRepository.findByValue(resetPasswordDto.getConfirmationKeyValue());
        this.keyRepository.delete(confirmationKey);
        return "Your password has been updated successfully";
    }

    @Override
    public List<UserEntity> listUserAccounts() {
        return this.userRepository.findAll();
    }

    @Override
    public String enableAccount(Long userId) {
        UserEntity userEntity = this.userRepository.findById(userId).get();
        userEntity.setAccountStatus(true);
        this.userRepository.save(userEntity);
        return "Account enabled";
    }

    @Override
    public String disableAccount(Long userId) {
        UserEntity userEntity = this.userRepository.findById(userId).get();
        userEntity.setAccountStatus(false);
        this.userRepository.save(userEntity);
        return "Account disabled";
    }

    @Override
    public UserEntity getProfile(String userName) {
        return this.userRepository.findByUserName(userName).get();
    }

    @Override
    public String updateProfile(String userName, UpdateProfileDto updateProfileDto) {
        UserEntity userEntity = this.userRepository.findByUserName(userName).get();
        userEntity.setFirstName(updateProfileDto.getFirstName());
        userEntity.setLastName(updateProfileDto.getLastName());
        userEntity.setPhoneNumber(updateProfileDto.getPhoneNumber());
        this.userRepository.save(userEntity);
        return "Profile updated";
    }

    @Override
    public String addPassedEvaluation(Long userId, Long evaluationId) {
        Eleve eleve = this.eleveRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Evaluation evaluation = this.evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new NotFoundException("Evaluation not found"));

        eleve.getPassedEvaluations().add(evaluation);
        this.userRepository.save(eleve);

        return "Evaluation added to passed evaluations successfully";
    }


    @Override
    public String changePassword(String userName, ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmation()))
            throw new BadRequestException("Confirm your password again");
        UserEntity userEntity = this.userRepository.findByUserName(userName).get();
        userEntity.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        this.userRepository.save(userEntity);
        return "Password updated";
    }

    private void validateResetPassword(ResetPasswordDto resetPasswordDto) {
        this.checkIfConfirmationKeyValueIsValid(resetPasswordDto.getConfirmationKeyValue());
        this.validateNewPassword(resetPasswordDto.getNewPassword(), resetPasswordDto.getConfirmedNewPassword());
    }

    private void checkIfConfirmationKeyValueIsValid(String confirmationKeyValue) {
        if (!this.keyRepository.existsByValue(confirmationKeyValue))
            throw new BadRequestException("confirmation key value invalid");
    }

    private void validateNewPassword(String newPassword, String confirmedNewPassword) {
        if (!Objects.equals(newPassword, confirmedNewPassword))
            throw new BadRequestException("New password confirmation invalid");
    }

    private void checkIfUserHasGeneratedAKey(String emailAddress) {
        if (this.keyRepository.existsByEmailAddress(emailAddress))
            throw new BadRequestException("We have already sent an email to reset your password");
    }

    private UserEntity getUserByEmailAddress(String emailAddress) {
        return this.userRepository.findByEmailAddress(emailAddress).orElseThrow(() -> new NotFoundException("Email address invalid"));
    }

    private void generateAndPersistKey(String keyValue, String emailAddress) {
        ConfirmationKey confirmationKey = new ConfirmationKey();
        confirmationKey.setEmailAddress(emailAddress);
        confirmationKey.setValue(keyValue);
        keyRepository.save(confirmationKey);
    }
}
