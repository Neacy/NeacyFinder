# NeacyFinder

simple FindViewById and SetOnClickListener by APT.

# how to use

@BindView(R.id.fab)
FloatingActionButton fab;

@OnClick({R.id.fab})
public void fabClick() {
    Toast.makeText(this, "Neacy", Toast.LENGTH_LONG).show();
}