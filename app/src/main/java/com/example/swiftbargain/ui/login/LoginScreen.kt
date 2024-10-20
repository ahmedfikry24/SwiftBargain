package com.example.swiftbargain.ui.login

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.BuildConfig
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.Authentication
import com.example.swiftbargain.navigation.Register
import com.example.swiftbargain.navigation.User
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryDialog
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.composable.SocialSignButton
import com.example.swiftbargain.ui.login.composable.ForgetPasswordBottomSheet
import com.example.swiftbargain.ui.login.view_model.LoginEvents
import com.example.swiftbargain.ui.login.view_model.LoginInteractions
import com.example.swiftbargain.ui.login.view_model.LoginUiState
import com.example.swiftbargain.ui.login.view_model.LoginViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showError
import com.example.swiftbargain.ui.utils.SnackBarManager.showWarning
import com.example.swiftbargain.ui.utils.UiConstants
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    mainVavController: NavController,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBar = SnackBarManager.init()
    EventHandler(viewModel.event) { events, scope ->
        when (events) {
            LoginEvents.InvalidEmailOrPassword -> snackBar.showError(
                UiConstants.INVALID_AUTH,
                scope
            )

            LoginEvents.CredentialFailed -> snackBar.showError(
                UiConstants.INVALID_CREDENTIAL,
                scope
            )

            LoginEvents.EmailNotRegister -> snackBar.showError(
                UiConstants.EMAIL_NOT_REGISTER,
                scope
            )

            LoginEvents.NoInternetConnection -> snackBar.showWarning(
                UiConstants.NO_INTER_NET_CONNECTION,
                scope
            )

            LoginEvents.SomeThingWentWrong -> snackBar.showError(
                UiConstants.SOME_THING_WENT_WRONG,
                scope
            )

            LoginEvents.NavToRegister -> navController.navigate(Register)

            LoginEvents.LoginSuccess -> mainVavController.navigate(User) {
                popUpTo(Authentication) {
                    inclusive = true
                }
            }

            LoginEvents.EmailAlreadyUsed -> snackBar.showError(
                UiConstants.EMAIL_ALREADY_USED,
                scope
            )
        }
    }
    LoginContent(
        state = state,
        interactions = viewModel
    )
}

@Composable
private fun LoginContent(
    state: LoginUiState,
    interactions: LoginInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val facebookLauncher = rememberSignInFacebookLauncher(interactions::loginWithFaceBook)
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
                    text = stringResource(R.string.welcome_to_swift_bargain),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colors.text
                )
            }
            item {
                Text(
                    text = stringResource(R.string.sign_in_to_continue),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colors.textGrey
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
                    leadingIconId = R.drawable.ic_email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
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
                    imeAction = ImeAction.Done,
                    visualTransformation = PasswordVisualTransformation(),
                    onChangeValue = interactions::onChangePassword
                )
            }

            item {
                PrimaryTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space16),
                    text = stringResource(R.string.sign_in),
                    onClick = interactions::loginWithEmailAndPassword
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
                    text = stringResource(R.string.login_with_google),
                    iconId = R.drawable.ic_google,
                    onClick = {
                        getGoogleCredential(context, scope) {
                            interactions.loginWithGoogle(
                                it
                            )
                        }
                    }
                )
            }
            item {
                SocialSignButton(
                    text = stringResource(R.string.login_with_facebook),
                    iconId = R.drawable.ic_facebook,
                    onClick = { facebookLauncher.launch(listOf("email", "public_profile")) }
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.space16)
                        .clickable(onClick = interactions::controlResetPasswordBottomSheetVisibility),
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.primary
                )
            }
            item {
                Row(modifier = Modifier.padding(bottom = MaterialTheme.spacing.space16)) {
                    Text(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                        text = stringResource(R.string.don_t_have_a_account),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colors.textGrey
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacing.space8)
                            .clickable(onClick = interactions::onRegister),
                        text = stringResource(R.string.register),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
    if (state.unVerifiedEmailDialog)
        PrimaryDialog(
            title = stringResource(R.string.error),
            text = stringResource(R.string.your_email_address_is_not_verified_check_your_email_and_verify_it_then_signin_again),
            onConfirm = interactions::controlUnVerifiedEmailDialogVisibility,
            onDismiss = interactions::controlUnVerifiedEmailDialogVisibility
        )
    if (state.resetPasswordBottomSheet)
        ForgetPasswordBottomSheet(
            value = state.forgetPasswordEmail,
            isError = state.forgetPasswordEmailError,
            onChangeValue = interactions::onChangeForgetPasswordEmail,
            onSend = interactions::onSendResetPasswordEmail,
            onDismiss = interactions::controlResetPasswordBottomSheetVisibility
        )
    if (state.resetPasswordDialog)
        PrimaryDialog(
            title = stringResource(R.string.success),
            text = stringResource(R.string.we_sent_a_reset_password_email_check_you_email),
            onConfirm = {
                interactions.controlResetPasswordBottomSheetVisibility()
                interactions.controlResetPasswordDialogVisibility()
            },
            onDismiss = interactions::controlResetPasswordDialogVisibility
        )
}


private fun getGoogleCredential(
    context: Context,
    coroutineScope: CoroutineScope,
    onCredentialResponse: (Credential) -> Unit
) {
    val credentialManager = CredentialManager.create(context)
    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.clientId)
        .setAutoSelectEnabled(false)
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            onCredentialResponse(result.credential)
        } catch (_: Throwable) {
        }
    }
}

@Composable
private fun rememberSignInFacebookLauncher(onSuccess: (String) -> Unit): ManagedActivityResultLauncher<Collection<String>, CallbackManager.ActivityResultParameters> {
    val context = LocalContext.current
    val loginManager = LoginManager.getInstance()
    val callbackManager = CallbackManager.Factory.create()
    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onCancel() {}

        override fun onError(error: FacebookException) {
            showFaceBookLoginFailure(context)
        }

        override fun onSuccess(result: LoginResult) {
            onSuccess(result.accessToken.token)
        }
    })
    return rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(
            callbackManager,
            null
        )
    ) {}
}

private fun showFaceBookLoginFailure(context: Context) {
    Toast.makeText(context, "some thing went wrong with facebook login", Toast.LENGTH_SHORT).show()
}