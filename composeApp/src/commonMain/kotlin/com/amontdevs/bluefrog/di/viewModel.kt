package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.ui.screens.home.ManualModeViewModel
import com.amontdevs.bluefrog.ui.screens.login.login.LoginViewModel
import com.amontdevs.bluefrog.ui.screens.login.setup.BasicSetupViewModel
import com.amontdevs.bluefrog.ui.screens.login.signin.SignInViewModel
import com.amontdevs.bluefrog.ui.screens.login.start.StartViewModel
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteSessionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule =
    module {
        // login
        viewModel { StartViewModel(get(), get()) }
        viewModel { SignInViewModel(get(), get()) }
        viewModel { LoginViewModel(get(), get()) }

        // absolute session
        viewModel { params ->
            AbsoluteSessionViewModel(
                get(),
                get { parametersOf(params.get<Int>()) },
            )
        }
        viewModel { ManualModeViewModel() }
        viewModel { BasicSetupViewModel() }
    }
