package msku.ceng.madlab.mytagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<Post>  posts = new ArrayList<>();
    Button btnPost;
    static final  int POST_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        PostAdapter postAdapter = new PostAdapter(this,posts);

        listView.setAdapter(postAdapter);

        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivityForResult(intent, POST_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_REQUEST && resultCode == MainActivity.RESULT_OK){
            Post post = new Post();
            post.setImage(data.getParcelableExtra("bitmap"));
            post.setMessage(data.getCharSequenceExtra("msg").toString());
            posts.add(post);
            ((PostAdapter)listView.getAdapter()).notifyDataSetChanged();
        }
    }
}