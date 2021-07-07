import CombineSchedulers
import Foundation
import Resolver
import Shared

extension Resolver: ResolverRegistering {
    public static func registerAllServices() {
        registerDataSources()
        registerRepositories()
        registerSchedulers()
        registerViewModels()
    }

    // MARK: - Data

    private static func registerDataSources() {
        register { HttpClientProvider().get() }
            .scope(.application)

        register { GitHubService(httpClient: resolve()) }
            .scope(.application)

        register { AppDatabaseProvider().get() }
            .scope(.application)
    }

    private static func registerRepositories() {
        register {
            UserRepositoryCombine(
                delegate: UserRepositoryIos.Provider().get(
                    gitHubService: resolve(),
                    appDatabase: resolve()
                )
            )
        }.implements(UserRepository.self)
            .scope(.application)

        register {
            RepoRepositoryCombine(
                delegate: RepoRepositoryIos.Provider().get(
                    gitHubService: resolve(),
                    appDatabase: resolve()
                )
            )
        }.implements(RepoRepository.self)
            .scope(.application)
    }

    // MARK: - UI

    private static func registerSchedulers() {
        register(name: .main) {
            DispatchQueue.main.eraseToAnyScheduler()
        }
    }

    private static func registerViewModels() {
        register {
            SearchViewModel(
                repoRepository: resolve(),
                mainScheduler: resolve(name: .main)
            )
        }

        register {
            RepoViewModel(
                repoRepository: resolve(),
                mainScheduler: resolve(name: .main)
            )
        }
    }
}

extension Resolver.Name {
    static let main = Self("main")
}
