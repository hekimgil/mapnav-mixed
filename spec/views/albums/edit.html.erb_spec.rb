require 'rails_helper'

RSpec.describe "albums/edit", :type => :view do
  before(:each) do
    @album = assign(:album, Album.create!(
      :title => "MyString",
      :center => "MyString",
      :owner => "MyString"
    ))
  end

  it "renders the edit album form" do
    render

    assert_select "form[action=?][method=?]", album_path(@album), "post" do

      assert_select "input#album_title[name=?]", "album[title]"

      assert_select "input#album_center[name=?]", "album[center]"

      assert_select "input#album_owner[name=?]", "album[owner]"
    end
  end
end
