package com.fernandomantoan.chucknorrisapp.di.data

import com.fernandomantoan.chucknorrisapp.di.data.local.LocalModule
import com.fernandomantoan.chucknorrisapp.di.data.remote.RemoteModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [LocalModule::class, RemoteModule::class])
class DataModule