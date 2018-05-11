package office.small.gviewer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import office.small.gviewer.di.InfoModelModule;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.model.entity.GithubUser;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;
import android.support.test.runner.AndroidJUnit4;
import javax.inject.Inject;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class InfoModelImplTest {

    private static MockWebServer mockWebServer;
    private static final String login = "login";
    private static final String avatarUrl = "http://example.com/avatar";

    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Inject
    InfoModelImpl model;

    @Inject
    RealmConfiguration configuration;

    @Before
    public void prepare() throws IOException {
        DaggerInfoModelTestComponent.builder()
                .infoModelModule(new InfoModelModule() {
                    @Override
                    public String provideEndpoint() {
                        return mockWebServer.url("/").toString();
                    }
                })
                .build()
                .inject(this);
    }

    @Test
    public void emptyAtStart() {
        TestSubscriber<Boolean> sub = TestSubscriber.create();
        model.lifecycle().map((list) -> {
            return list.isEmpty();
        }).first().subscribe(sub);
        sub.awaitTerminalEvent();
        sub.assertValue(true);
    }

    @Test
    public void lifecycleUpdate() {
        mockWebServer.enqueue(createMockResponse(login, avatarUrl));

        TestSubscriber<Boolean> sub = TestSubscriber.create();
        model.lifecycle().filter(l -> !l.isEmpty()).map(list -> {
            GithubUser user = list.get(0);
            return user.getLogin().equals(login) && user.getAvatarURL().equals(avatarUrl);
        }).first().subscribe(sub);
        model.updateInfo().subscribe();
        sub.awaitTerminalEvent();
        sub.assertValue(true);
    }

    private MockResponse createMockResponse(String login, String avatarUrl) {
        return new MockResponse().setBody("{\n" +
                "\"login\": \"" + login + "\",\n" +
                "\"avatar_url\": \"" + avatarUrl + "\",\n" +
                "}");
    }

    @After
    public void dispose() throws InterruptedException {
        while (Realm.getGlobalInstanceCount(configuration) > 0) {
            Thread.sleep(100);
        }
        Realm.deleteRealm(configuration);
    }

    @BeforeClass
    public static void setupServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void shutdownServer() throws IOException {
        mockWebServer.shutdown();
    }
}