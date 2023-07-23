# EyojCryptoAppWithKoin

Bu proje, kripto para birimleri verilerini Coinpaprika API'sini kullanarak alarak, kripto para birimleriyle ilgili bilgilerin listelendiği bir uygulama geliştirmeyi amaçlayan bir MVVM (Model-View-ViewModel) mimarisi projesidir.

Uygulama mantığı ve işlemleri ViewModel katmanında yönetilir. ViewModel'ler, verileri tutar ve kullanıcı etkileşimlerini işler. "StateFlow" ve "Flow" kullanarak veri akışı sağlanır ve böylece kullanıcı arayüzünde anlık güncellemeler gerçekleştirilir.

 Kullanılan Kütüphaneler 

Retrofit: Coinpaprika API'sine çağrılar yaparak güncel verileri alır.

Room: Verileri yerel olarak saklamak için kullanılır.Böylece, internet bağlantısı olmadığında dahi kullanıcılar daha önce indirilen verilere erişebilir ve uygulamanın performansı artar.

Koin: Bağımlılık yönetimi için kullanılır. Bağımlılıkların kolayca yönetilmesini sağlar ve uygulama kodunun düzenli ve bakımı kolay hale gelir.

Navigation : Uygulamada gezinme ve ekran geçişlerini yönetmek için kullanılır.

Glide : Uygulamada kripto para birimleriyle ilgili resimleri yüklemek ve göstermek için oldukça kullanışlıdır.

StateFlow: Bu kütüphane, verilerin akışını takip etmek ve kullanıcı arayüzünün anlık güncellenmesini sağlamak için kullanılabilir. StateFlow ise Kotlin Coroutine'lerle kullanılan bir akış API'sidir.

This project is an MVVM architecture project aimed at developing a cryptocurrency application that retrieves cryptocurrency data using the Coinpaprika API.

The application logic and operations are managed in the ViewModel layer. ViewModels hold the data and handle user interactions. Data flow is facilitated using "StateFlow" and "Flow", enabling real-time updates on the user interface.

Used Libraries:

1- Retrofit: It retrieves current data by making API calls to the Coinpaprika API.

2- Room: It is used for local data storage. This allows users to access previously downloaded data even when there is no internet connection, enhancing application performance.

3- Koin: It is used for dependency management. Koin simplifies the management of dependencies and makes the application code organized and easy to maintain.

4- Navigation: It is used to manage navigation and screen transitions within the application.

5- Glide: It is highly useful for loading and displaying images related to cryptocurrency within the application.

6- StateFlow: This library is used to track data flow and provide real-time updates to the user interface. StateFlow is an API used with Kotlin Coroutines.
