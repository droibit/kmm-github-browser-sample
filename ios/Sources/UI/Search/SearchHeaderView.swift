import SwiftUI

struct SearchHeaderView: View {
    private let onSubmitQuery: (String) -> Void

    @State private var query: String = ""

    init(onSubmitQuery: @escaping (String) -> Void) {
        self.onSubmitQuery = onSubmitQuery
    }

    var body: some View {
        TextField("Search repositories", text: $query, onCommit: {
            self.onSubmitQuery(query)
        })
            .textFieldStyle(PlainTextFieldStyle())
            .textContentType(.none)
            .autocapitalization(.none)
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .background(Color.gray.opacity(0.25))
            .cornerRadius(8.0)
    }
}

struct SearchHeaderView_Previews: PreviewProvider {
    static var previews: some View {
        SearchHeaderView { _ in
        }
        .background(Color(UIColor.systemBackground))
        .previewLayout(.sizeThatFits)
        .environment(\.colorScheme, .light)

        SearchHeaderView { _ in
        }
        .background(Color(UIColor.systemBackground))
        .previewLayout(.sizeThatFits)
        .environment(\.colorScheme, .dark)
    }
}
