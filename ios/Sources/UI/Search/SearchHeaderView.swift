import SwiftUI

struct SearchHeaderView: View {
    private let onSubmitQuery: () -> Void

    private let disabled: Bool

    private var query: Binding<String>

    init(query: Binding<String>, disabled: Bool, onSubmitQuery: @escaping () -> Void) {
        self.query = query
        self.disabled = disabled
        self.onSubmitQuery = onSubmitQuery
    }

    var body: some View {
        TextField("Search repositories", text: query, onCommit: {
            self.onSubmitQuery()
        })
            .disabled(disabled)
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
    struct QueryContainer: View {
        @State private var query: String = ""

        var body: some View {
            SearchHeaderView(query: $query, disabled: false) {}
        }
    }

    static var previews: some View {
        Group {
            QueryContainer()
                .background(Color(UIColor.systemBackground))
                .preferredColorScheme(.light)

            QueryContainer()
                .background(Color(UIColor.systemBackground))
                .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
