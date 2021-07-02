import SwiftUI

struct SearchHeaderView: View {
    private let onSubmitQuery: (String) -> Void

    private let disabled: Bool

    @State private var query: String = ""

    init(disabled: Bool, onSubmitQuery: @escaping (String) -> Void) {
        self.disabled = disabled
        self.onSubmitQuery = onSubmitQuery
    }

    var body: some View {
        TextField("Search repositories", text: $query, onCommit: {
            self.onSubmitQuery(query)
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
    static var previews: some View {
        Group {
            SearchHeaderView(disabled: false) { _ in
            }
            .background(Color(UIColor.systemBackground))
            .preferredColorScheme(.light)

            SearchHeaderView(disabled: true) { _ in
            }
            .background(Color(UIColor.systemBackground))
            .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
