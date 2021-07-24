import CombineSchedulers
import Foundation
import NeedleFoundation
import Shared

class AppComponent: BootstrapComponent {}

// MARK: - Binding

private enum AppComponentProvider {
    static var instance = AppComponent()
}

func get<T>(_ keyPath: KeyPath<AppComponent, T>) -> T {
//    SingletonComponent.instance[keyPath: keyPath]
    AppComponentProvider.instance[keyPath: keyPath]
}

// MARK: - UI

extension AppComponent {
    var mainScheduler: AnySchedulerOf<DispatchQueue> {
        shared {
            DispatchQueue.main.eraseToAnyScheduler()
        }
    }

    var searchViewModel: SearchViewModel {
        SearchViewModel(
            repoRepository: repoRepository,
            mainScheduler: mainScheduler
        )
    }

    var repoViewModel: RepoViewModel {
        RepoViewModel(
            repoRepository: repoRepository,
            mainScheduler: mainScheduler
        )
    }

    var userViewModel: UserViewModel {
        UserViewModel(
            userRepository: userRepository,
            repoRepository: repoRepository,
            mainScheduler: mainScheduler
        )
    }
}

// MARK: - Repository

extension AppComponent {
    var userRepository: UserRepository {
        shared {
            UserRepositoryCombine(
                delegate: UserRepositoryIos.Provider().get(
                    gitHubService: gitHubService,
                    appDatabase: appDatabase
                )
            )
        }
    }

    var repoRepository: RepoRepository {
        shared {
            RepoRepositoryCombine(
                delegate: RepoRepositoryIos.Provider().get(
                    gitHubService: gitHubService,
                    appDatabase: appDatabase
                )
            )
        }
    }
}

// MARK: - Data Source

extension AppComponent {
    var httpClient: Ktor_client_coreHttpClient {
        shared { HttpClientProvider().get() }
    }

    var gitHubService: GitHubService {
        shared {
            GitHubService(httpClient: httpClient)
        }
    }

    var appDatabase: AppDatabase {
        shared { AppDatabaseProvider().get() }
    }
}
