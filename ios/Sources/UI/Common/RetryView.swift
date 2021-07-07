import SwiftUI

struct RetryView: View {
    private let message: String

    private let retryAction: () -> Void

    init(message: String, retryAction: @escaping () -> Void = {}) {
        self.message = message
        self.retryAction = retryAction
    }

    var body: some View {
        VStack {
            Text(message)
                .padding(.horizontal)
            Button("Retry", action: retryAction)
                .padding()
        }
        .font(.body)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct RetryView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            RetryView(message: "Error")
                .preferredColorScheme(.light)

            RetryView(message: "Error")
                .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
