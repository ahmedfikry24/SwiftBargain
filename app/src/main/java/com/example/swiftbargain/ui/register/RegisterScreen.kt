package com.example.swiftbargain.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.composable.SocialSignButton
import com.example.swiftbargain.ui.register.viiew_model.RegisterEvents
import com.example.swiftbargain.ui.register.viiew_model.RegisterInteractions
import com.example.swiftbargain.ui.register.viiew_model.RegisterUiState
import com.example.swiftbargain.ui.register.viiew_model.RegisterViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showError
import com.example.swiftbargain.ui.utils.SnackBarManager.showSuccess
import com.example.swiftbargain.ui.utils.SnackBarManager.showWarning
import com.example.swiftbargain.ui.utils.UiConstants

@Composable
fun RegisterScreen(
    maniNavController: NavController,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBar = SnackBarManager.init()
    EventHandler(viewModel.event) { events, scope ->
        when (events) {
            RegisterEvents.NavigateToLogin -> snackBar.showSuccess(
                UiConstants.REGISTER_SUCCESS,
                scope
            ) { navController.popBackStack() }

            RegisterEvents.NoInternetConnection -> snackBar.showWarning(
                UiConstants.NO_INTER_NET_CONNECTION,
                scope
            )

            RegisterEvents.RegistrationFailed -> snackBar.showError(
                UiConstants.REGISTRATION_FAILED,
                scope
            )

            RegisterEvents.SomeThingWentWrong -> snackBar.showError(
                UiConstants.SOME_THING_WENT_WRONG,
                scope
            )

            RegisterEvents.EmailIsAlreadyUsed -> snackBar.showError(
                UiConstants.EMAIL_ALREADY_USED,
                scope
            )
        }
    }

    RegisterContent(state = state, interactions = viewModel)
}

@Composable
private fun RegisterContent(
    state: RegisterUiState,
    interactions: RegisterInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space24 + MaterialTheme.spacing.space24),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
                    contentDescription = null
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                    text = stringResource(R.string.let_s_get_started),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colors.text
                )
            }
            item {
                Text(
                    text = stringResource(R.string.create_an_new_account),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colors.textGrey
                )
            }

            item {
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space16),
                    value = state.name,
                    hint = stringResource(R.string.full_name),
                    isError = state.emailError,
                    leadingIconId = R.drawable.ic_profile,
                    keyboardType = KeyboardType.Email,
                    onChangeValue = interactions::onChangeName
                )
            }
            item {
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space16),
                    value = state.email,
                    hint = stringResource(R.string.example_gmail_com),
                    isError = state.emailError,
                    errorText = stringResource(R.string.invalid_email),
                    leadingIconId = R.drawable.ic_email,
                    keyboardType = KeyboardType.Email,
                    onChangeValue = interactions::onChangeEmail
                )
            }
            item {
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space8),
                    value = state.password,
                    hint = stringResource(R.string.password),
                    isError = state.passwordError,
                    errorText = stringResource(R.string.password_must_be_more_than_6_characters),
                    leadingIconId = R.drawable.ic_password,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onChangeValue = interactions::onChangePassword
                )
            }
            item {
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space8),
                    value = state.passwordAgain,
                    hint = stringResource(R.string.password_again),
                    isError = state.passwordAgainError,
                    errorText = stringResource(R.string.password_mismatch),
                    leadingIconId = R.drawable.ic_password,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onChangeValue = interactions::onChangePasswordAgain
                )
            }
            item {
                PrimaryTextButton(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                    text = stringResource(R.string.sign_up),
                    onClick = interactions::registerWithEmailAndPassword
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textGrey
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.space4),
                        text = stringResource(R.string.or),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colors.textGrey
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textGrey
                    )
                }
            }
            item {
                SocialSignButton(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                    text = stringResource(R.string.sign_up_with_google),
                    iconId = R.drawable.ic_google,
                    onClick = { }
                )
            }
            item {
                SocialSignButton(
                    text = stringResource(R.string.sign_up_with_facebook),
                    iconId = R.drawable.ic_facebook,
                    onClick = { }
                )
            }
            item {
                Row(modifier = Modifier.padding(bottom = MaterialTheme.spacing.space16)) {
                    Text(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        text = stringResource(R.string.have_a_account),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colors.textGrey
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacing.space8)
                            .clickable(onClick = interactions::onSignIn),
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}
