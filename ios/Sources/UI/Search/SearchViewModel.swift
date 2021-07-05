import Combine
import CombineSchedulers
import Foundation
import Shared

class SearchViewModel: ObservableObject {
    private let repoRepository: RepoRepository

    private let mainScheduler: AnySchedulerOf<DispatchQueue>

    private var lastQuery: String

    private var cancellables = Set<AnyCancellable>()

    @Published private(set) var searchResultUiModel: SearchResultUiModel

    @Published var query: String

    init(repoRepository: RepoRepository,
         mainScheduler: AnySchedulerOf<DispatchQueue>,
         query: String = "",
         lastQuery: String = "",
         searchResultUiModel: SearchResultUiModel = .init())
    {
        self.repoRepository = repoRepository
        self.mainScheduler = mainScheduler
        self.query = query
        self.lastQuery = lastQuery
        self.searchResultUiModel = searchResultUiModel
    }

    deinit {
        Komol.d("deinit: \(type(of: self))")
    }

    func searchWithNewQuery() {
        guard query != lastQuery else {
            return
        }
        search()
    }

    func search() {
        let query = self.query.lowercased().trimmingCharacters(in: .whitespaces)
        guard !query.isEmpty else {
            lastQuery = query
            searchResultUiModel = SearchResultUiModel()
            return
        }
        guard !searchResultUiModel.inProgress else {
            return
        }

        let mayPaging = query == lastQuery
        let currentSearchResult: PagedRepoSearchResult?
        if mayPaging {
            guard let searchResult = searchResultUiModel.searchResult,
                  searchResult.nextPage != nil
            else {
                return
            }
            currentSearchResult = searchResult
        } else {
            currentSearchResult = nil
        }
        searchResultUiModel = SearchResultUiModel(inProgress: true, searchResult: currentSearchResult)

        let page: Int? = currentSearchResult?.nextPage?.intValue
        Komol.d("query=\(query), page=\(String(describing: page))")
        repoRepository.search(query: query, page: page)
            .receive(on: mainScheduler)
            .sink(with: self) { owner, completion in
                if case let .failure(error) = completion {
                    owner.searchResultUiModel = SearchResultUiModel(
                        searchResult: currentSearchResult,
                        error: error.localizedDescription
                    )
                }
            } receiveValue: { _, newSearchResult in
                defer { self.lastQuery = query }
                if mayPaging {
                    let currentRepos = currentSearchResult?.repos ?? []
                    self.searchResultUiModel = SearchResultUiModel(
                        searchResult: newSearchResult.merge(existingRepos: currentRepos)
                    )
                } else {
                    self.searchResultUiModel = SearchResultUiModel(searchResult: newSearchResult)
                }
            }.store(in: &cancellables)
    }
}
