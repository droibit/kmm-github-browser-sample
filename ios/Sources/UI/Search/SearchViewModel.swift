import Combine
import CombineSchedulers
import Foundation
import Shared

class SearchViewModel: ObservableObject {
    private let repoRepository: RepoRepository

    private let mainScheduler: AnySchedulerOf<DispatchQueue>

    private var mergedPagedRepoSearchResult: PagedRepoSearchResult?

    private var cancellables = Set<AnyCancellable>()

    @Published private(set) var searchResultUiModel = SearchResultUiModel()

    @Published var query: String

    init(repoRepository: RepoRepository,
         mainScheduler: AnySchedulerOf<DispatchQueue>,
         query: String = "",
         searchResultUiModel: SearchResultUiModel = .init())
    {
        self.repoRepository = repoRepository
        self.mainScheduler = mainScheduler
        self.query = query
        self.searchResultUiModel = searchResultUiModel
    }

    func search() {
        guard !searchResultUiModel.inProgress else { return }
        guard !query.isEmpty else {
            searchResultUiModel = SearchResultUiModel(searchResult: PagedRepoSearchResult())
            return
        }

        let searchResult = searchResultUiModel.searchResult
        searchResultUiModel = SearchResultUiModel(inProgress: true, searchResult: searchResult)

        let page: Int? = searchResult?.nextPage?.intValue
        repoRepository.search(query: query, page: page)
            .subscribe(on: mainScheduler)
            .sink { [weak self] completion in
                if case let .failure(error) = completion {
                    self?.searchResultUiModel = SearchResultUiModel(
                        searchResult: searchResult,
                        error: error.localizedDescription
                    )
                }
            } receiveValue: { [weak self] newSearchResult in
                self?.searchResultUiModel = SearchResultUiModel(
                    searchResult: newSearchResult.merge(existingRepos: searchResult?.repos ?? [])
                )
            }.store(in: &cancellables)
    }
}
