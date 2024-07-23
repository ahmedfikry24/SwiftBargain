package com.example.swiftbargain.ui.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.BuildConfig
import com.example.swiftbargain.MainActivity
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.Authentication
import com.example.swiftbargain.navigation.Register
import com.example.swiftbargain.navigation.User
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.login.view_model.LoginEvents
import com.example.swiftbargain.ui.login.view_model.LoginInteractions
import com.example.swiftbargain.ui.login.view_model.LoginUiState
import com.example.swiftbargain.ui.login.view_model.LoginViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showError
import com.example.swiftbargain.ui.utils.SnackBarManager.showWarning
import com.example.swiftbargain.ui.utils.UiConstants
import com.example.swiftbargain.ui.utils.eventHandler
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(
    mainVavController: NavController,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBar = SnackBarManager.init()
    eventHandler(viewModel.event, scope) {
        when (it) {
            LoginEvents.InvalidEmailOrPassword -> snackBar.showError(
                UiConstants.INVALID_AUTH,
                scope
            )

            LoginEvents.LoginSuccess -> mainVavController.navigate(User) {
                popUpTo(Authentication) {
                    inclusive = true
                }
            }

            LoginEvents.NoInternetConnection -> snackBar.showWarning(
                UiConstants.NO_INTER_NET_CONNECTION,
                scope
            )

            LoginEvents.SomeThingWentWrong -> snackBar.showError(
                UiConstants.SOME_THING_WENT_WRONG,
                scope
            )

            LoginEvents.CredentialFailed -> snackBar.showError(
                UiConstants.INVALID_CREDENTIAL,
                scope
            )

            LoginEvents.NavToRegister -> navController.navigate(Register)
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
    val googleSignInClient = rememberGoogleSignInClient()
    val signInGoogleLauncher = rememberSignInGoogleLauncher {
        interactions.getGoogleCredential(
            it.data ?: return@rememberSignInGoogleLauncher
        )
    }
    val facebookLauncher = rememberSignInFacebookLauncher(interactions::loginWithFaceBook)
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
                    leadingIconId = R.drawable.ic_password,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onChangeValue = interactions::onChangePassword
                )
            }

            item {
                PrimaryTextButton(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
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
                SocialLoginButton(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                    text = stringResource(R.string.login_with_google),
                    iconId = R.drawable.ic_google,
                    onClick = { signInGoogleLauncher.launch(googleSignInClient.signInIntent) }
                )
            }
            item {
                SocialLoginButton(
                    text = stringResource(R.string.login_with_facebook),
                    iconId = R.drawable.ic_facebook,
                    onClick = { facebookLauncher.launch(listOf("email", "public_profile")) }
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.space16)
                        .clickable(onClick = interactions::onForgetPassword),
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
}


@Composable
private fun SocialLoginButton(
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colors.background),
        border = BorderStroke(1.dp, MaterialTheme.colors.textGrey),
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        contentPadding = PaddingValues(MaterialTheme.spacing.space16),
        onClick = onClick,
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = ImageVector.vectorResource(iconId),
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.textGrey,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun rememberGoogleSignInClient(): GoogleSignInClient {
    val context = LocalContext.current as MainActivity
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.clientId)
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

@Composable
private fun rememberSignInGoogleLauncher(onSuccess: (ActivityResult) -> Unit): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == RESULT_OK) {
            onSuccess(result)
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