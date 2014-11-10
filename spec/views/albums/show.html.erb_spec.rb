require 'rails_helper'

RSpec.describe "albums/show", :type => :view do
  before(:each) do
    @album = assign(:album, Album.create!(
      :title => "Title",
      :center => "Center",
      :owner => "Owner"
    ))
  end

  it "renders attributes in <p>" do
    render
    expect(rendered).to match(/Title/)
    expect(rendered).to match(/Center/)
    expect(rendered).to match(/Owner/)
  end
end
